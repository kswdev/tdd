package net.study.tdd.person.infrastructure.jdbc;

import net.study.tdd.person.dao.PersonDao;
import net.study.tdd.person.domain.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPersonDao implements PersonDao {

    private DataSource datasource;
    protected final static String FIND_BY_LASTNAME_SQL = "SELECT * FROM people WHERE last_name = ?";

    @Override
    public Person find(Long id) {
        return null;
    }

    @Override
    public void save(Person person) {

    }

    @Override
    public void update(Person person) {

    }

    @Override
    public void delete(Person person) {

    }

    @Override
    public List<Person> findAll() {
        return List.of();
    }

    @Override
    public List<Person> findByLastname(String lastname) {
        try {
            Connection conn = datasource.getConnection();
            PreparedStatement psmt = conn.prepareStatement(FIND_BY_LASTNAME_SQL);
            psmt.setString(1, lastname);
            ResultSet rset = psmt.executeQuery();
            List<Person> people = new ArrayList<>();
            while (rset.next()) {
                String firstName = rset.getString("first_name");
                String lastName = rset.getString("last_name");
                people.add(new Person(firstName, lastName));
            }
            rset.close();
            psmt.close();
            conn.close();
            return people;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }
}
