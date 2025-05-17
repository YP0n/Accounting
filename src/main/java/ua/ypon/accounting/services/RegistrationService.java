package ua.ypon.accounting.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.ypon.accounting.models.Person;
import ua.ypon.accounting.repositories.PersonRepository;

@Service
@AllArgsConstructor
public class RegistrationService {

    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Person person) {
        person.setUsername(person.getUsername());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        
        if(personRepository.count() == 0) {
            person.setRole("ROLE_ADMIN");
        } else {
            person.setRole("ROLE_USER");
        }
        personRepository.save(person);
    }

    @Transactional
    public void loginService(Person person) {
        personRepository.findByUsername(person.getUsername());
    }
}
