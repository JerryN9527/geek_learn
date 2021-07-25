package com.example.spring;

import io.kimmking.Klass;
import io.kimmking.School;
import io.kimmking.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private Student student;
    @Autowired
    private School school;
    @Autowired
    private Klass klass;

    @Bean
    public void sout(){
        System.out.println(student.toString());
        System.out.println(klass.toString());
        System.out.println(school.toString());
    }

}
