package com.example.javagradlek8sapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@RestController
class HomeController {
    @GetMapping("/")
    public String home() {
        return "Hello from Spring Boot app running in Kubernetes!";
    }
}
