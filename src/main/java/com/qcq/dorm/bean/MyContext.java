package com.qcq.dorm.bean;

import com.qcq.dorm.service.impl.HelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MyContext {
    @Autowired
    ApplicationContext context;

    public void sayHello(){
    	HelloServiceImpl service=(HelloServiceImpl) context.getBean("helloServiceImpl");
        System.out.println(service.sayHello());
    }

}
