package com.example.demo.Client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class ClientConfig {

    private final PasswordEncoder passwordEncoder;

    public ClientConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

    }


    @Bean
    CommandLineRunner commandLineRunner(ClientRepository repository){
        return args -> {
            Client marian = new Client(
                    "user",
                    "Kamil",
                    passwordEncoder.encode("pass"),
                    "kamil.kowalski@interia.pl",
                    LocalDate.of(1999, Month.AUGUST,2),
                    22

            );

            Client zbyszek = new Client(
                    "admin",
                    "Zbigniew",
                     passwordEncoder.encode("password"),
                    "zbigniew.romański@interia.pl",
                    LocalDate.of(1997, Month.JULY,24),
                    24
            );
            Client trzeci = new Client(
                    "user",
                    "Zbigniew",
                    passwordEncoder.encode("Kowalski"),
                    "zbigniew.kowalski@interia.pl",
                    LocalDate.of(1998, Month.JUNE,16),
                    23

            );

            repository.saveAll(
                    List.of(marian,zbyszek,trzeci)
            );

        };
    }
}
