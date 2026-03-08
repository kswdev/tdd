package net.study.tdd.person.infrastructure.jdbcTemplate;

import net.study.tdd.person.domain.Person;
import net.study.tdd.person.infrastructure.PersonSql;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTemplatePersonDao {

    private JdbcTemplate jdbcTemplate;
    private PersonRowMapper rowMapper;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setRowMapper(PersonRowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

    public List<Person> findByLastname(String lastname) {
        return jdbcTemplate.query(
                PersonSql.FIND_BY_LASTNAME_SQL,
                new Object[] {lastname},
                rowMapper
        );
    }
}
