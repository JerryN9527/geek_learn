package io.kimmking;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("io.kimmking")
@RequiredArgsConstructor
public class SpringAutoConfiguration {


    @Bean(name = "student111")
    public Student createStudent(){
        return  new Student(888,"student111");
    }

    @Bean(name = "class1")
    public Klass createKlass(@Qualifier("student111") Student student){
        List<Student> list = new ArrayList<>();
        list.add(student);
        return  new Klass(list);
    }

}
