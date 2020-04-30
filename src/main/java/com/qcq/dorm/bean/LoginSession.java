package com.qcq.dorm.bean;

import com.qcq.dorm.dto.LoginResultDTO;
import com.qcq.dorm.entity.DormOfficer;
import com.qcq.dorm.entity.Student;
import com.qcq.dorm.enums.UserEnum;
import com.qcq.dorm.service.DormOfficerService;
import com.qcq.dorm.service.StudentService;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 在线用户
 * TODO 调用接口前判断是否已经在指定登陆的ip进行操作
 *
 * @author O
 * @since 2020/2/17
 */
@Component
public class LoginSession {
    /**
     * Long:id
     * LocalDateTime:过期时间
     * 过期时间存在，则说明登陆过，大于当下时间，说明正在登陆，小于则说明在线时间过期了
     */
    private static HashMap<Long, LoginUser> students = new HashMap<>(256);
    private static HashMap<Long, LoginUser> dormOfficers = new HashMap<>(256);

    @Resource
    private StudentService studentService;
    @Resource
    private DormOfficerService dormOfficerService;

    public LoginResultDTO login(UserEnum userEnum, Long id, String pwd, HttpServletRequest request) {
        LoginResultDTO result = new LoginResultDTO();
        if (!checkPassword(userEnum, id, pwd)) {
            result.setSuccess(false);
            result.setMessage("用户名不存在或者密码错误");
            return result;
        }
        HttpSession session = request.getSession();
        LoginUser loginUser = getLoginUser(userEnum, id);

        //缓存中没有该用户，第一次登陆
        if (loginUser == null) {
            loginUser = LoginUser.builder()
                    .id(id)
                    .ip(request.getRemoteHost())
                    .expireAt(LocalDateTime.now().plusMinutes(60))
                    .build();
            putLoginUser(userEnum, loginUser);
        } else {
            //用户已经登陆
            //本地重复登陆
            if (request.getRemoteHost().equals(loginUser.getIp())) {
                result.setSuccess(true);
                //重新刷新时间
                loginUser.setExpireAt(LocalDateTime.now().plusMinutes(60));
            } else {
                //异地登陆且session过期，则可以登陆成功，否则登陆失败
                if (LocalDateTime.now().isAfter(loginUser.getExpireAt())) {
                    result.setSuccess(false);
                    result.setMessage("用户已经在其它设备登陆，请先退出登陆");
                    return result;
                }
            }
        }
        session.setAttribute("expireAt", LocalDateTime.now().plusMinutes(60));
        session.setAttribute("id", id);
        session.setAttribute("userType", UserEnum.STUDENT.getName());
        result.setSuccess(true);
        result.setMessage("登陆成功");
        return result;
    }

    public boolean logout(UserEnum user, Long id, HttpSession session) {
        if (user.equals(UserEnum.STUDENT)) {
            students.remove(id);
        } else if (user.equals(UserEnum.DORM_OFFICER)) {
            dormOfficers.remove(id);
        } else {
            return false;
        }
        session.setAttribute("expireAt", LocalDateTime.now());
        return true;
    }

    private boolean checkPassword(UserEnum userEnum, Long id, String password) {
        if (userEnum.equals(UserEnum.STUDENT)) {
            Student stu = studentService.selectById(id);
            return null != stu && stu.getPassword().equals(password);
        } else if (userEnum.equals(UserEnum.DORM_OFFICER)) {
            DormOfficer officer = dormOfficerService.selectById(id);
            return null != officer && officer.getPassword().equals(password);
        } else {
            return false;
        }
    }

    private LoginUser getLoginUser(UserEnum userEnum, Long id) {
        if (userEnum.equals(UserEnum.STUDENT)) {
            return students.get(id);
        } else if (userEnum.equals(UserEnum.DORM_OFFICER)) {
            return dormOfficers.get(id);
        } else {
            return null;
        }
    }

    private void putLoginUser(UserEnum userEnum, LoginUser loginUser) {
        if (userEnum.equals(UserEnum.STUDENT)) {
            students.put(loginUser.getId(), loginUser);
        } else if (userEnum.equals(UserEnum.DORM_OFFICER)) {
            dormOfficers.put(loginUser.getId(), loginUser);
        }
    }

    @Data
    @Builder
    private static class LoginUser {
        private String ip;
        private LocalDateTime expireAt;
        /**
         * 用户id
         */
        private Long id;
    }
}

