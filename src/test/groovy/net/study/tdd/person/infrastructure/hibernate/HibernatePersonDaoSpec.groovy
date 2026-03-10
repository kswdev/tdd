package net.study.tdd.person.infrastructure.hibernate

import net.study.tdd.person.domain.Person
import org.hibernate.HibernateException
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.query.Query
import spock.lang.Specification

class HibernatePersonDaoSpec extends Specification {

    private SessionFactory factory = Mock()
    private Session session = Mock()
    private Query query = Mock()

    private HibernatePersonDao dao

    def setup() {
        dao = new HibernatePersonDao()
    }

    def "성으로 사람을 조회한다."() {
        given:
        String hql = HibernatePersonDao.FIND_BY_LASTNAME
        String lastname = "Smith"

        def smiths = [
                new Person("John", lastname),
                new Person("Jane", lastname)
        ]

        dao.setSessionFactory(factory)

        when:
        def people = dao.findByLastname(lastname)

        then:
        1 * factory.getCurrentSession() >> session
        1 * session.createQuery(hql) >> query
        1 * query.setParameter("lastname", lastname) >> query
        1 * query.list() >> smiths

        people == smiths
    }

    def "성으로 사람을 조회하면 unchecked 에러가 발생한다."() {
        given:
        String hql = HibernatePersonDao.FIND_BY_LASTNAME
        String lastname = "Smith"
        HibernateException hibernateError = new HibernateException("")

        dao.setSessionFactory(factory)

        when:
        dao.findByLastname(lastname)

        then:
        1 * factory.getCurrentSession() >> session
        1 * session.createQuery(hql) >> query
        1 * query.setParameter("lastname", lastname) >> query
        1 * query.list() >> { throw hibernateError }

        def ex = thrown(RuntimeException.class)
        ex.cause == hibernateError
    }
}
