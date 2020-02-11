package com.qcq.dorm.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
//第二种值初始化方式

//@PropertySource(value = "classpath:person.properties")
@Component
public class Husband {
    //这个表达式是直接赋值
//    @Value("husband1")
    //这个表达式是取yml里面的变量
    //单个注解
    @Value("${person.name}")
    private String name;
    @Value("#{11*2}")
    private int age;

    @Override
    public String toString() {
        return "Husband{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
