package com.demo.JPAExample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
Implement the following methods and present usage for retrieving all persons
whose birthdate is:
(a) Between two certain dates
(b) On a given month
(c) In a given year
Implement the following modifying methods and present usage:
(a) Add Mr. as a prefix to the name of all MALEs
(b) Delete all persons whose birthdate is in the future
Present usage of Paging and Sorting in your application:
(a) Print all persons in groups of 2 per page, sorted by birthdate
(b) Print all persons who were born on this month, in groups of 2 per page
 */

@SpringBootApplication
public class JpaExampleApplication {

	private static final Logger log = LoggerFactory.getLogger(JpaExampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JpaExampleApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(PersonRepository personRepository) {
		return (args) -> {
			//save persons
			List<Person> persons = new ArrayList<>();
			persons.add(new Person("Paz", LocalDate.of(1988, 8, 10), Person.Gender.FEMALE));
			persons.add(new Person("Orel", LocalDate.of(1989, 4, 28), Person.Gender.MALE));
			persons.add(new Person("Adam", LocalDate.of(2020, 2, 8), Person.Gender.MALE));
			persons.add(new Person("Aviv", LocalDate.of(2020, 10, 9), Person.Gender.MALE));
			persons.add(new Person("Maayan", LocalDate.of(1988, 8, 10), Person.Gender.FEMALE));
			persons.add(new Person("FutureBaby", LocalDate.of(2021, 4, 1), Person.Gender.FEMALE));

			personRepository.saveAll(persons);

			//fetch all persons
			log.info("persons found with findAll()");
			persons = personRepository.findAll();
			persons.forEach(person -> log.info(person.toString()));
			log.info("------------------------");

			//1.(a) - Between two certain dates
			log.info("between two certain dates");
			LocalDate start = LocalDate.of(2019, 10, 1);
			LocalDate end = LocalDate.of(2021, 3, 1);
			persons = personRepository.findByBirthDateBetween(start, end);
			persons.forEach(person -> log.info(person.toString()));
			log.info("------------------------");

			//1.(b) On a given month
			log.info("On a given month");
			persons = personRepository.findAllBornInMonth(8);
			persons.forEach(person -> log.info(person.toString()));
			log.info("------------------------");

			//1.(c) On a given year
			log.info("On a given year");
			persons = personRepository.findAllBornInYear(1988);
			persons.forEach(person -> log.info(person.toString()));
			log.info("------------------------");

			//2.(a) Add Mr. as a prefix to the name of all MALEs
			log.info("Add Mr. as a prefix to the name of all MALEs");
			personRepository.addTitleToMales(Person.Gender.MALE);
			persons = personRepository.findAll();
			persons.forEach(person -> log.info(person.toString()));
			log.info("------------------------");

			//2.(b) Delete all persons whose birthdate is in the future
			log.info("Delete all persons whose birthdate is in the future");
			persons = personRepository.findAllBornInFuture();
			persons.forEach(person -> personRepository.deleteById(person.getId()));
			log.info("FutureBaby is deleted");
			personRepository.findAll().forEach(person -> log.info(person.toString()));
			log.info("------------------------");

			//3.(a) Print all persons in groups of 2 per page, sorted by birthdate
			log.info("Print all persons in groups of 2 per page, sorted by birthdate");
			Page<Person> page;
			int pageIndex = 0;
			int pageSize = 2;
			do {
				Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("birthDate").ascending());
				page = personRepository.findAll(pageable);
				++pageIndex;
				log.info("Page " + pageIndex);
				log.info("out of " + page.getTotalPages());
				page.forEach(person -> log.info(person.toString()));
			} while(!page.isLast());
			log.info("------------------------");

			//3.(b) Print all persons who were born on this month, in groups of 2 per page
			log.info("Print all persons who were born on this month, in groups of 2 per page");
			pageIndex = 0;
			pageSize = 2;
			do {
				Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("birthDate").ascending());
				page = personRepository.findAllBornInMonth(8, pageable);
				++pageIndex;
				log.info("Page " + pageIndex);
				log.info("out of " + page.getTotalPages());
				page.forEach(person -> log.info(person.toString()));
			} while(!page.isLast());
			log.info("------------------------");

			log.info("");
		};
	}
}
