package com.psrs;

import com.psrs.model.Role;
import com.psrs.model.User;
import com.psrs.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PublicServiceRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicServiceRequestApplication.class, args);
    }

    /** Creates the admin account on first run if none exists. */
    @Bean
    CommandLineRunner seedAdmin(UserRepository users, PasswordEncoder encoder,
                                @Value("${app.admin.email}") String email,
                                @Value("${app.admin.password}") String password) {
        return args -> {
            if (!users.existsByRole(Role.ADMIN)) {
                users.save(new User("County Admin", email.toLowerCase(), encoder.encode(password), Role.ADMIN));
            }
        };
    }
}
