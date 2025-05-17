package ua.ypon.accounting.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.ypon.accounting.models.Person;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class PersonDetails implements UserDetails {
    
    private final Person person;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(person.getRole().split(", "))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
    
    @Override
    public String getPassword() {
        return person.getPassword();
    }
    
    @Override
    public String getUsername() {
        return person.getUsername();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
