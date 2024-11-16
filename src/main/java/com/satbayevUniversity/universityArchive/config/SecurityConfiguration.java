package com.satbayevUniversity.universityArchive.config;

import com.satbayevUniversity.universityArchive.domain.User;
import com.satbayevUniversity.universityArchive.domain.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username);
            if (user == null) throw new UsernameNotFoundException("Username '" + username + "' not found.");

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getAuthorities()
            );
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/login", "/register")
                        .permitAll()

                        .requestMatchers("/", "/students/records", "/students/records/{id}", "/students/records/list", "/shelves", "/shelves/{id}", "/shelves/list",
                                "/profile", "/profile/**", "/employees/records/list", "/employees/records/{id}")
                        .hasAnyRole("Employee", "Archivist", "Admin")

                        .requestMatchers("/shelves/archivist/**", "/students/records/archivist", "/students/records/archivist/**",
                                "/employees/records/archivist/**")
                        .hasAnyRole("Archivist", "Admin")

                        .requestMatchers("/admin", "/admin/**").hasRole("Admin")
                        .anyRequest().denyAll()
        ).formLogin(
                form -> form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/", true)
        ).logout(logout -> {
            logout.logoutSuccessUrl("/login?logout").permitAll();
        });

        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {web.ignoring().requestMatchers("/images/**", "/css/**");};
    }
}

