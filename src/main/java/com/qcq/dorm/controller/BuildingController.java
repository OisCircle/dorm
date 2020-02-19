package com.qcq.dorm.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.BuildingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 楼栋 前端控制器
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/building")
public class BuildingController {
    @Resource
    private BuildingService buildingService;
    @GetMapping
    public CommonResult getAll() {
        return CommonResult.success(buildingService.selectList(new EntityWrapper<>()));
    }
}

