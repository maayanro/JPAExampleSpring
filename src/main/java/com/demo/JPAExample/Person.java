package com.demo.JPAExample;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Person {

    public enum Gender {MALE, FEMALE}

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    protected Person() {
    }

    public Person(String name, LocalDate birthDate, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long ID) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                '}';
    }
}
