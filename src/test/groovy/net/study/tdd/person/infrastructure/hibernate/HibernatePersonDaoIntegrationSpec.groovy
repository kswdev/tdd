package net.study.tdd.person.infrastructure.hibernate

import net.study.tdd.person.domain.Person
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import spock.lang.Specification

class HibernatePersonDaoIntegrationSpec extends Specification {

    def "영속성 객체가 데이터베이스에 존재하는지 확인한다."() {
        given:
        SessionFactory sf = getSessionFactory()
        HibernatePersonDao dao = new HibernatePersonDao()
        dao.setSessionFactory(sf)
        Person person = new Person("John", "Doe")

        when:
        dao.save(person)

        then:
        person.getId() != null
        with(sf.openSession().get(Person, person.getId())) {
            it == person
        }
    }

    def getSessionFactory() {
        return createConfiguration().buildSessionFactory()
    }

    def createConfiguration() {
        Configuration cfg = loadProductionConfiguration();
        loadTestConfigInto(cfg, "/hibernate.test.properties");
        return cfg;
    }

    def loadProductionConfiguration() {
        return new Configuration().configure()
    }

    def loadTestConfigInto(Configuration cfg, String path) {
        Properties properties = loadPropertiesFrom(path);
        Enumeration keys = properties.keys()
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement()
            String value = properties.getProperty(key)
            cfg.setProperty(key, value)
        }
    }

    def loadPropertiesFrom(String path) throws Exception {
        InputStream stream = getClass().getResourceAsStream(path)
        Properties props = new Properties()
        props.load(stream)
        stream.close()
        return props
    }
}
