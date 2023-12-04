package br.com.sgq.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.sgq.api")
public class SgApp {

    public static void main(String[] args) {
        SpringApplication.run(SgApp.class, args);
    }

}
