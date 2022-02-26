package com.eddiejrojas.SXMproject.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DatabaseStarter {
    private static final Logger log = LoggerFactory.getLogger(DatabaseStarter.class);

    @Bean
    CommandLineRunner initDatabase(ContentRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Content("Why are you like this", "terrible", "", "", "" )));
            log.info("Preloading " + repository.save(new Content("Why are you like this", "terrible", "", "", "" )));
            log.info("Preloading " + repository.save(new Content("Why are you like this", "terrible", "", "", "" )));
            log.info("Preloading " + repository.save(new Content("Why are you like this", "terrible", "", "", "" )));
            log.info("Preloading " + repository.save(new Content("Why are you like this", "terrible", "", "", "" )));
            log.info("Preloading " + repository.save(new Content("Why are you like this", "terrible", "", "", "" )));
            log.info("Preloading " + repository.save(new Content("Why are you like this", "terrible", "", "", "" )));
        };
    }
}
