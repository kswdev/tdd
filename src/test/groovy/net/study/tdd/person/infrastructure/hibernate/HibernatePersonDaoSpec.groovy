package net.study.tdd.person.infrastructure.hibernate

import net.study.tdd.person.domain.Person
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.query.Query
import spock.lang.Specification

class HibernatePersonDaoSpec extends Specification {

    private SessionFactory factory = Stub()
    private Session session = Stub()
    private Query query = Stub()

    def "성으로 사람을 조회한다."() {
        given:
        String hql = HibernatePersonDao.FIND_BY_LASTNAME
        String lastname = "Smith"

        def smiths = [
                new Person("John", lastname),
                new Person("Jane", lastname)
        ]

        factory.getCurrentSession() >> session
        session.createQuery(hql) >> query
        query.setParameter("lastname", lastname) >> query
        query.list() >> smiths
        HibernatePersonDao dao = new HibernatePersonDao()
        dao.setSessionFactory(factory)

        when:
        def people = dao.findByLastname(lastname)

        then:
        people == smiths
    }
}
