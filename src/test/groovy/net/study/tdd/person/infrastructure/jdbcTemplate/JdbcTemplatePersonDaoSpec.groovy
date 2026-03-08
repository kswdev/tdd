package net.study.tdd.person.infrastructure.jdbcTemplate

import net.study.tdd.person.domain.Person
import net.study.tdd.person.infrastructure.PersonSql
import org.springframework.jdbc.core.JdbcTemplate
import spock.lang.Specification

class JdbcTemplatePersonDaoSpec extends Specification {

    private JdbcTemplate template = Stub()
    private JdbcTemplatePersonDao personDao

    def setup() {
        personDao = new JdbcTemplatePersonDao()
    }

    def "성으로 사람을 조회한다."() {
        given:
        String lastname = "smith"

        List<Person> smiths = List.of(
                new Person("Alice", "Smith"),
                new Person("Billy", "Smith"),
                new Person("Clark", "Smith"))

        def rowMapper = new PersonRowMapper()

        template.query(
                PersonSql.FIND_BY_LASTNAME_SQL,
                new Object[] {lastname},
                rowMapper) >> smiths

        personDao.setJdbcTemplate(template)
        personDao.setRowMapper(rowMapper)

        when:
        List<Person> results = personDao.findByLastname(lastname)

        then:
        results == smiths
    }
}
