package io.kimmking;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemoApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student123 = (Student) context.getBean("student111");
        System.out.println(student123.toString());

        Student student100 = (Student) context.getBean("student999");
        System.out.println(student100.toString());

        Klass klass = (Klass) context.getBean("class1");
        System.out.println(klass.toString());
    }
}
