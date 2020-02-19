package com.qcq.dorm.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qcq.dorm.service.BuildingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * @author O
 * @since 2020/2/18
 */
@Controller
public class RedirectController {
    @Resource
    private BuildingService buildingService;

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/index")
    public String index1() {
        return "/index";
    }

    @GetMapping("/studentRegister")
    public String studentRegister() {
        return "/studentRegister";
    }

    @GetMapping("/dormOfficerRegister")
    public String dormOfficerRegister(Model model) {
        model.addAttribute("buildings", buildingService.selectList(new EntityWrapper<>()));
        return "/dormOfficerRegister";
    }

    @GetMapping("/studentIndex")
    public String studentIndex(Long id, Model model) {
        model.addAttribute("id", id);
        return "/studentIndex";
    }

    @GetMapping("/dormOfficerIndex")
    public String dormOfficerIndex(Long id, Model model) {
        model.addAttribute("id", id);
        return "/dormOfficerIndex";
    }
}
