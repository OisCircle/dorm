package com.qcq.dorm.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Map;

//测试在yml里面进行初始化配置该类
//第一种值初始化方式

//加载指定的配置文件
//@PropertySource(value = "classpath:person.yml")
//JSR303数据校验
@Validated
//告诉springboot这个类里面的所有属性都是yml文件里面的属性，与yml进行绑定
//批量注解
@ConfigurationProperties(prefix = "person")
//注册到容器中
@Component
public class Person {
    //校验是否是邮箱格式
//    @Email
    private String name;
    private Boolean alive;

    private Date birthDate;

    private int age;
    private Map<String,Object> map;
    private List<Object> list;
    private Dog dog;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", alive=" + alive +
                ", birthDate=" + birthDate +
                ", age=" + age +
                ", map=" + map +
                ", list=" + list +
                ", dog=" + dog +
                '}';
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, Object> getMap() {
        return map;
    }


    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
