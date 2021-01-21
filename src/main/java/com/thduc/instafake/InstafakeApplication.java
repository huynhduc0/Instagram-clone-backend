package com.thduc.instafake;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.common.collect.Lists;
import com.thduc.instafake.entity.Roles;
import com.thduc.instafake.entity.User;
import com.thduc.instafake.repository.RoleRepository;
import com.thduc.instafake.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin(origins = "*")
@ConfigurationProperties("instafake")
public class InstafakeApplication {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) throws IOException {
//        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/Users/thduc/Desktop/HCI/instafake/src/main/resources/hihi-8a82316783dd.json"))
//                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-vision"));
//
//        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
//
//        System.out.println("Buckets:");
//        Page<Bucket> buckets = storage.list();
//        for (Bucket bucket : buckets.iterateAll()) {
//            System.out.println(bucket.toString());
//        }

        SpringApplication.run(InstafakeApplication.class, args);
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    @ConditionalOnMissingBean
    public CloudVisionTemplate cloudVisionTemplate(ImageAnnotatorClient imageAnnotatorClient) {
        return new CloudVisionTemplate(imageAnnotatorClient);
    }
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
