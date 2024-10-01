package com.assessment;

import com.assessment.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class RentalAgreementsApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
        ctx.close();
        SpringApplication.run(RentalAgreementsApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            if (log.isDebugEnabled()) {
                log.debug("message from application.properties {}", environment.getProperty("message-from-application-properties"));
            }
        };
    }
}
