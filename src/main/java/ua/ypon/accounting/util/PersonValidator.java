package ua.ypon.accounting.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.ypon.accounting.models.Person;
import ua.ypon.accounting.services.PersonDetailsServices;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsServices personDetailsServices;

    @Autowired
    public PersonValidator(PersonDetailsServices personDetailsServices) {
        this.personDetailsServices = personDetailsServices;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        try {
            personDetailsServices.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "","Користувач з таким нікнеймом вже існує!");
    }
}
