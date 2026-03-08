package net.study.tdd.person.infrastructure.jdbc

import net.study.tdd.person.domain.Person
import net.study.tdd.person.infrastructure.PersonSql
import spock.lang.Specification

import javax.sql.DataSource
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class JdbcPersonDaoTest extends Specification {

    private DataSource dataSource = Mock()
    private Connection connection = Mock()
    private PreparedStatement psmt = Mock()
    private ResultSet resultSet = Mock()

    def "성으로 사람을 조회한다."() {
        given:
        def dao = new JdbcPersonDao()
        dao.setDatasource(dataSource)

        def smiths = [
                new Person("John", "Smith"),
                new Person("Jane", "Smith")
        ]

        when:
        def people = dao.findByLastname("Smith")

        then:
        1 * dataSource.getConnection() >> connection
        1 * connection.prepareStatement(PersonSql.FIND_BY_LASTNAME_SQL ) >> psmt
        1 * psmt.setString(1, "Smith")
        1 * psmt.executeQuery() >> resultSet

        // ResultSet iteration
        resultSet.next() >>> [true, true, false]

        resultSet.getString("first_name") >>> ["John", "Jane"]
        resultSet.getString("last_name") >>> ["Smith", "Smith"]

        1 * resultSet.close()
        1 * psmt.close()
        1 * connection.close()

        people == smiths
    }
}
