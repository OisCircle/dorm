package com.qcq.dorm.controller;


import com.qcq.dorm.entity.FixForm;
import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.FixFormService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 维修工单 前端控制器
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/fixForm")
public class FixFormController {
    @Resource
    private FixFormService fixFormService;

    @PostMapping("/submit")
    public CommonResult submit(@Validated FixForm form, BindingResult br) {
        if (br.hasErrors() && br.getFieldError() != null) {
            return CommonResult.failure(br.getFieldError().getDefaultMessage());
        }
        form.setState(0);
        form.setSubmitTime(LocalDateTime.now());
        return CommonResult.expect(fixFormService.insert(form));
    }

    @PutMapping("/update")
    public CommonResult fix(FixForm form) {
        if (form == null || form.getId() == null) {
            return CommonResult.failure("参数错误");
        }
        return CommonResult.expect(form.updateById());
    }
}

