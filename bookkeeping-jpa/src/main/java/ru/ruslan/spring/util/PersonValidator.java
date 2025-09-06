package ru.ruslan.spring.util;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ruslan.spring.models.Person;
import ru.ruslan.spring.services.PersonService;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personService.findOnePerson(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Это ФИО уже существует");
    }
}
