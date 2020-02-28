package com.qcq.dorm.controller;


import com.qcq.dorm.bean.LoginSession;
import com.qcq.dorm.dto.LoginResultDTO;
import com.qcq.dorm.entity.Bed;
import com.qcq.dorm.entity.Student;
import com.qcq.dorm.enums.UserEnum;
import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.BedService;
import com.qcq.dorm.service.StudentService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource
    private LoginSession loginSession;
    @Resource
    private BedService bedService;

    @PostMapping("/register")
    @ResponseBody
    CommonResult register(@Validated Student student, BindingResult br) {
        if (br.hasErrors() && br.getFieldError() != null) {
            return CommonResult.failure(br.getFieldError().getDefaultMessage());
        }

        final boolean success;
        try {
            success = studentService.insert(student);
        } catch (DuplicateKeyException e) {
            return CommonResult.failure("学生已经存在，请登陆");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failure("系统错误");
        }
        if (!success) {
            return CommonResult.failure();
        }
        return CommonResult.success();
    }

    @PostMapping("/login")
    @ResponseBody
    CommonResult login(Student student, HttpServletRequest request) {
        final Long id = student.getId();
        final String pwd = student.getPassword();
        if (id == null || pwd == null || pwd.isEmpty()) {
            return CommonResult.failure("账号密码不能为空");
        }
        final LoginResultDTO result = loginSession.login(UserEnum.STUDENT, student.getId(), student.getPassword(), request);
        return CommonResult.expect(result.getSuccess()).setMessage(result.getMessage());
    }

    @GetMapping("/logout")
    @ResponseBody
    CommonResult logout(Long id, HttpSession session, ModelAndView mv) {
        loginSession.logout(UserEnum.STUDENT, id, session);
        return CommonResult.success();
    }

    @PutMapping("/update")
    @ResponseBody
    CommonResult update(@Validated Student student, BindingResult br) {
        if (br.hasErrors() && br.getFieldError() != null) {
            return CommonResult.failure(br.getFieldError().getDefaultMessage());
        }
        final boolean result = studentService.updateById(student);
        return CommonResult.expect(result);
    }

    @PostMapping("/chooseBed")
    @ResponseBody
    CommonResult chooseBed(Long id, Long bedId) {
        final Student student = studentService.selectById(id);
        final Long oldBedId = student.getBedId();
        student.setBedId(bedId);
        if (student.updateById()) {
            final Bed oldBed = bedService.selectById(oldBedId);
            final Bed newBed = bedService.selectById(bedId);
            oldBed.setIsSelected(0);
            oldBed.setIsMoveIn(0);
            newBed.setIsSelected(1);
            newBed.setIsMoveIn(0);
            return CommonResult.success(newBed.updateById() && oldBed.updateById());
        } else {
            return CommonResult.failure("学生信息更新失败");
        }
    }
}

