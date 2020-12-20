package com.thduc.instafake;

import com.thduc.instafake.entity.Roles;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.repository.RoleRepository;
import com.thduc.instafake.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class InstafakeApplication {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {

        SpringApplication.run(InstafakeApplication.class, args);
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner initData(UserRepository userRepository, RoleRepository rolesRespository) {
        return args -> {
//            if (rolesRespository.findAll().isEmpty()) {
//                List<String> rolesName = Arrays.asList("ADMIN", "USER");
//                rolesName.forEach(r -> {
//                    Roles role = new Roles();
//                    role.setRolename(r);
//                    rolesRespository.saveAndFlush(role);
//                });
//            }

            if (userRepository.count() == 0) {

                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("congchuabuoito"));
                user.setEmail("thduc.17it3@vku.udn.vn");
//                user.setRoles(new HashSet<>(rolesRespository.findAll()));
                HashSet s = new HashSet();
                s.add(new Roles("ADMIN"));
                s.add(new Roles("USER"));
                user.setRoles(s);
                userRepository.save(user);
            }
        };
    }


}
