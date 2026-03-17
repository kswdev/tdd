package net.study.tdd.person.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Person {

    @Id
    private Long id;
    private String firstname;
    private String lastname;

    public Person(String firstname, String lastname) {
        id = UUID.randomUUID().getMostSignificantBits();
        this.firstname = firstname;
        this.lastname = lastname;
    }

    protected Person() {}

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstname, person.firstname) && Objects.equals(lastname, person.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }
}
