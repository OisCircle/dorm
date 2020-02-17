package com.qcq.dorm.controller;

import com.qcq.dorm.bean.Husband;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@ResponseBody在这里写的话，所有方法的返回值都变为json格式
//@RestController之后，相当于@Controller+@ResponseBody
@Controller
public class HelloController {
	@Autowired
	Husband husband;
    //在配置文件里面取值
    @Value("${person.name}")
    private String name;
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello(){
        return "hello world...";
    }

    @RequestMapping(value = "/sayHello")
    @ResponseBody
    public String sayHello(){
        return "hello: "+name;
    }

    @RequestMapping(value = "/success")
	public String success(Model model){
		model.addAttribute("husband",this.husband);
		model.addAttribute("a","asd");
		model.addAttribute("b","qwe");
		model.addAttribute("c","fgh");
		model.addAttribute("d","tyu");
	    return "success";
    }
}