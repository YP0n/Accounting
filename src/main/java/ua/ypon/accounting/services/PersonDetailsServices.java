package ua.ypon.accounting.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.ypon.accounting.models.Person;
import ua.ypon.accounting.repositories.PersonRepository;
import ua.ypon.accounting.security.PersonDetails;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsServices implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<Person> person = personRepository.findByUsername(username);
        
        return person.map(PersonDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found " + username));
    }
}
