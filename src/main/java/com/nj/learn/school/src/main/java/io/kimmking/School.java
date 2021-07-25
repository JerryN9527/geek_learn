package io.kimmking;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Data
@Component
public class School implements ISchool {
    
    // Resource 
    @Autowired(required = true) //primary
            Klass class1;
    
    @Resource(name = "student111")
    Student student100;
    
    @Override
    public void ding(){
    
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
        
    }
    
}
