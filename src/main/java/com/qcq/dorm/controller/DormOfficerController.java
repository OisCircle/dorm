package com.qcq.dorm.controller;


import com.qcq.dorm.bean.LoginSession;
import com.qcq.dorm.dto.LoginResultDTO;
import com.qcq.dorm.entity.DormOfficer;
import com.qcq.dorm.enums.UserEnum;
import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.DormOfficerService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 宿管 前端控制器
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/dormOfficer")
public class DormOfficerController {
    @Resource
    private DormOfficerService dormOfficerService;
    @Resource
    LoginSession loginSession;

    @PostMapping("/register")
    CommonResult register(@Validated DormOfficer officer, BindingResult br) {
        if (br.hasErrors() && br.getFieldError() != null) {
            return CommonResult.failure(br.getFieldError().getDefaultMessage());
        }

        final boolean success;
        try {
            success = dormOfficerService.insert(officer);
        } catch (DuplicateKeyException e) {
            return CommonResult.failure("宿管已经存在，请登陆");
        }
        if (!success) {
            return CommonResult.failure();
        }
        return CommonResult.success();
    }

    @PostMapping("/login")
    CommonResult login(DormOfficer officer, HttpServletRequest request) {
        final Long id = officer.getId();
        final String pwd = officer.getPassword();
        if (id == null || pwd == null || pwd.isEmpty()) {
            return CommonResult.failure("账号密码不能为空");
        }
        final LoginResultDTO result = loginSession.login(UserEnum.DORM_OFFICER, officer.getId(), officer.getPassword(), request);
        return CommonResult.expect(result.getSuccess()).setMessage(result.getMessage());
    }

    @PostMapping("/logout")
    CommonResult logout(Long id, HttpSession session) {
        return CommonResult.expect(loginSession.logout(UserEnum.DORM_OFFICER, id, session));
    }

    @PutMapping("/update")
    CommonResult update(@Validated DormOfficer officer, BindingResult br) {
        if (br.hasErrors() && br.getFieldError() != null) {
            return CommonResult.failure(br.getFieldError().getDefaultMessage());
        }
        final boolean result = dormOfficerService.updateById(officer);
        return CommonResult.expect(result);
    }
}

