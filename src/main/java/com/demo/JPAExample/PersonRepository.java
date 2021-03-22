package com.demo.JPAExample;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByBirthDateBetween(LocalDate start, LocalDate end);

    @Query("Select p from Person p where MONTH(p.birthDate) = :month")
    List<Person> findAllBornInMonth(int month);

    @Query("Select p from Person p where MONTH(p.birthDate) = :month")
    Page<Person> findAllBornInMonth(int month, Pageable pageable);

    @Query("Select p from Person p where YEAR(p.birthDate) = :year")
    List<Person> findAllBornInYear(int year);

    @Query("Select p from Person p where p.birthDate > CURRENT_DATE")
    List<Person> findAllBornInFuture();

    @Transactional
    @Modifying
    @Query("update Person p set p.name= CONCAT('Mr.', p.name) where p.gender=:gender")
    void addTitleToMales(Person.Gender gender);

}
