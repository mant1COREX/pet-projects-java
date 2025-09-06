package ru.ruslan.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.spring.models.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByFullName(String name);
}
