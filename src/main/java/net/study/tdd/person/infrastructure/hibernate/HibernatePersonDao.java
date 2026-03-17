package net.study.tdd.person.infrastructure.hibernate;

import net.study.tdd.person.domain.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class HibernatePersonDao {

    private SessionFactory sessionFactory;
    protected final static String FIND_BY_LASTNAME = "from Person p where p.lastname = :lastname";

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Person> findByLastname(String lastname) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(FIND_BY_LASTNAME);
            query.setParameter("lastname", lastname);
            return query.list();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }
}
