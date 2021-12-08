package com.example.demo.BookOrder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BookOrderConfig {
    @Bean
    CommandLineRunner commandLineRunnerOrder(BookOrderRepository repository){
        return args -> {
            BookOrder b1 = new BookOrder(
                    1L,
                    1L,
                    "kamil.kowalski@interia.pl",
                    "Hamlet",
                    false
            );

            BookOrder b2 = new BookOrder(
                    1L,
                    2L,
                    "kamil.kowalski@interia.pl",
                    "Romeo i Julia",
                    false
            );
            BookOrder b3 = new BookOrder(
                    1l,
                    3L,
                    "kamil.kowalski@interia.pl",
                    "Makbet",
                    true
            );

            repository.saveAll(
                    List.of(b1,b2,b3)
            );

        };
    }
}
