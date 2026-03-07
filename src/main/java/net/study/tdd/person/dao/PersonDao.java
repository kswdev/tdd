package net.study.tdd.person.dao;

import net.study.tdd.person.domain.Person;

import java.util.List;

public interface PersonDao {
    Person find(Long id);
    void save(Person person);
    void update(Person person);
    void delete(Person person);
    List<Person> findAll();
    List<Person> findByLastname(String lastname);
}
