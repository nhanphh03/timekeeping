package com.lottefn.collateral.domain.configs;

import com.lottefn.collateral.domain.entities.Role;
import com.lottefn.collateral.domain.entities.User;
import com.lottefn.collateral.domain.enums.SystemRole;
import com.lottefn.collateral.domain.repositories.RoleRepository;
import com.lottefn.collateral.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartRunner implements ApplicationRunner {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        if (!roleRepository.existsByName(SystemRole.ADMIN)) {
            Role role = Role.builder()
                    .name(SystemRole.ADMIN)
                    .description("")
                    .build();
            role = roleRepository.save(role);
            User user = User.builder()
                    .email("admin@gmail.com")
                    .username("admin@gmail.com")
                    .name("Admin")
                    .role(role)
                    .password(passwordEncoder.encode("123"))
                    .build();
            userRepository.save(user);
        }
    }

}
