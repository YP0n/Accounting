package ua.ypon.accounting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.ypon.accounting.services.PersonDetailsServices;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    public static final String LOGIN_URL = "/login";
    private final PersonDetailsServices personDetailsService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                LOGIN_URL,
                                "/registration",
                                "/error")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage(LOGIN_URL)
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl(LOGIN_URL + "?error"))
                .rememberMe(rememberMe -> rememberMe.userDetailsService(personDetailsService))
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl(LOGIN_URL).permitAll());
        return http.build();
    }
    
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(personDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
