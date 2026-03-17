package net.study.tdd.infra


import org.hibernate.cfg.Configuration
import spock.lang.Specification

class HibernateIntegrationSpec extends Specification {

    def getSessionFactory() {
        return createConfiguration().buildSessionFactory()
    }

    def createConfiguration() {
        Configuration cfg = loadProductionConfiguration()
        loadTestConfigInto(cfg, "/hibernate.test.properties")
        return cfg
    }

    def loadProductionConfiguration() {
        return new Configuration().configure()
    }

    def loadTestConfigInto(Configuration cfg, String path) {
        Properties properties = loadPropertiesFrom(path)
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
