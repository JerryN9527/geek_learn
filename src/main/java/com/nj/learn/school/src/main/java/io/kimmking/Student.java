package io.kimmking;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable {
//public class Student implements Serializable, BeanNameAware, ApplicationContextAware {

    private int id;
    private String name;

//    private String beanName;
//    private ApplicationContext applicationContext;
//
    
    public void init(){
        System.out.println("hello...........");
    }
    
    public static Student create(){
        return new Student(102,"KK101");
    }

//    public void print(){
//        System.out.println(this.beanName);
//        System.out.println("  context.getBeanDefintionNames() ===>> "
//        + String.join(",",applicationContext.getBeanDefinitionNames()));
//    }
//
}
