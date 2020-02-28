package com.qcq.dorm.controller;


import com.qcq.dorm.entity.DormChangeForm;
import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.DormChangeFormService;
import com.qcq.dorm.service.StudentService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 更换宿舍工单 前端控制器
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/dormChangeForm")
public class DormChangeFormController {
    @Resource
    private StudentService studentService;
    @Resource
    private DormChangeFormService dormChangeFormService;

    @PostMapping("/submit")
    public CommonResult submit(@Validated DormChangeForm form, BindingResult br) {
        if (br.hasErrors() && br.getFieldError() != null) {
            return CommonResult.failure(br.getFieldError().getDefaultMessage());
        }
        form.setState(0);
        final Long oldBedId = studentService.selectById(form.getStudentId()).getBedId();
        form.setOldBedId(oldBedId);
        return CommonResult.expect(dormChangeFormService.insert(form));
    }

    @PutMapping("/judge")
    public CommonResult judge(Long id, Boolean isAgree) {
        if (null == id || null == isAgree) {
            return CommonResult.failure("参数错误");
        }
        final DormChangeForm form = new DormChangeForm();
        form.setId(id);
        form.setState(isAgree ? 1 : 2);
        return CommonResult.expect(form.updateById());
    }
}

