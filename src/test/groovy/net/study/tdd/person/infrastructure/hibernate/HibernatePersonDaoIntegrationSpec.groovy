package net.study.tdd.person.infrastructure.hibernate

import net.study.tdd.infra.HibernateIntegrationSpec
import net.study.tdd.person.domain.Person
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction

class HibernatePersonDaoIntegrationSpec extends HibernateIntegrationSpec {

    private SessionFactory sf
    private Transaction tx
    private HibernatePersonDao dao
    def setup() {
        sf = getSessionFactory()
        tx = sf.getCurrentSession().beginTransaction()
        dao = new HibernatePersonDao()
        dao.setSessionFactory(sf)
    }

    def cleanup() {
        tx.rollback()
    }

    def "영속성 객체가 데이터베이스에 존재하는지 확인한다."() {
        given:
        Person person = new Person("John", "Doe")

        when:
        dao.save(person)

        then:
        person.getId() != null
        with(sf.getCurrentSession().get(Person, person.getId())) {
            it == person
        }
    }

    def "성이 smith 인 데이터를 모두 조회한다."() {
        given:
        List<Person> theSmiths = new ArrayList<>()
        theSmiths.add(new Person("Alice", "Smith"))
        theSmiths.add(new Person("Billy", "Smith"))
        List<Person> allPeople = new ArrayList<Person>()
        allPeople.addAll(theSmiths)
        allPeople.add(new Person("John", "Doe"))
        persist(allPeople)

        when:
        def result = dao.findByLastname("Smith")

        then:
        theSmiths == result
    }

    def persist(List<Person> people) {
        Session session = sf.getCurrentSession()
        for (Person person: people) {
            session.save(person)
        }
        session.flush()
    }
}
