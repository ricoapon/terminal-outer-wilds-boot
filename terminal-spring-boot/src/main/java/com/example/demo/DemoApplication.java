package com.example.demo;

import nl.ricoapon.terminal.backend.TerminalFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public TerminalFacade terminalFacade() {
        return new TerminalFacade();
    }
}
