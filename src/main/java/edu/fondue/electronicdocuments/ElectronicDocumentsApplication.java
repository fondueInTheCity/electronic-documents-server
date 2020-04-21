package edu.fondue.electronicdocuments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class ElectronicDocumentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicDocumentsApplication.class, args);
    }
}
