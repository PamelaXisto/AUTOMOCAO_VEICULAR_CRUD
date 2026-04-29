package br.com.fecaf.config;

import br.com.fecaf.enums.UserStatus;
import br.com.fecaf.model.Role;
import br.com.fecaf.model.User;
import br.com.fecaf.repository.RoleRepository;
import br.com.fecaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

//@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class SeedConfig {

    @Bean
    CommandLineRunner seedDatabase(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {

            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName("ADMIN");
                        return roleRepository.save(role);
                    });


            if (!userRepository.existsByEmail("admin@email.com")) {
                User admin = User.builder()
                        .email("admin@email.com")
                        .name("Admin")
                        .surname("System")
                        .passwordHash(passwordEncoder.encode("Admin@123456"))
                        .phone("11999999999")
                        .cpf("77610894001")
                        .birthDate(LocalDate.of(2000, 1, 1))
                        .role(adminRole)
                        .statusUser(UserStatus.ACTIVE)
                        .build();

                userRepository.save(admin);

                System.out.println("Admin criado com sucesso!");
            }
        };
    }
}

