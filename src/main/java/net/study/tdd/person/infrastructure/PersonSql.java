package net.study.tdd.person.infrastructure;

public abstract class PersonSql {
    private PersonSql() {}

    public static final String FIND_BY_LASTNAME_SQL = "SELECT * FROM people WHERE last_name = ?";
}
