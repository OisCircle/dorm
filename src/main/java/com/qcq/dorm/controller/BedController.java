package com.qcq.dorm.controller;


import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.BedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sun.misc.CRC16;

import javax.annotation.Resource;

/**
 * <p>
 * 床位 前端控制器
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/bed")
public class BedController {
    @Resource
    private BedService bedService;
    @GetMapping("/getById")
    CommonResult getById(Long id) {
        if (id == null) {
            return CommonResult.failure("id不能为空");
        }
        return CommonResult.success(bedService.selectById(id));
    }
}