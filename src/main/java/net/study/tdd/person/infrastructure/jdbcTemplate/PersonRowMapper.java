package net.study.tdd.person.infrastructure.jdbcTemplate;

import net.study.tdd.person.domain.Person;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Nullable
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(rs.getString("first_name"), rs.getString("last_name"));
    }
}
