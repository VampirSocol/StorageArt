package org.storageart.storageart;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.storageart.storageart.entities.User;
import org.storageart.storageart.repositories.UserRepository;

@SpringBootApplication
public class StorageArtApplication {

    private static final Logger log = LoggerFactory.getLogger(StorageArtApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StorageArtApplication.class, args);
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
//            repository.save(new User("noname", passwordEncoder.encode("1234")));
//            repository.save(new User("dendrit", passwordEncoder.encode("1234")));
//            repository.save(new User("veralin", passwordEncoder.encode("1234")));
//            repository.save(new User("planze", passwordEncoder.encode("1234")));
//            repository.save(new User("name", passwordEncoder.encode("1234")));

            log.info("Users found with findAll(): ");
            for(User user : repository.findAll()) {
                log.info(user.toString());
            }
            log.info("");

            log.info("Finding user with findByUsername('noname'): ");
            repository.findByUsername("noname").ifPresentOrElse(
                    user -> log.info("Found user with {}", user),
                    () -> log.info("There is no user with name noname")
            );
            log.info("");

            log.info("Finding user with findById(1L): ");
            repository.findById(1L).ifPresentOrElse(
                    user -> log.info("Found user with {}", user),
                    () -> log.info("There is no user with id 1")
            );
            log.info("");

//            repository.deleteAll();
//
//            log.info("Users found with findAll() after deleteAll(): ");
//            for(User user : repository.findAll()) {
//                log.info(user.toString());
//            }
//            log.info("The end");

        };
    }

}