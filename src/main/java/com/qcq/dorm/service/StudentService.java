package com.qcq.dorm.service;

import com.qcq.dorm.entity.Bed;
import com.qcq.dorm.entity.Student;
import com.baomidou.mybatisplus.service.IService;
import com.qcq.dorm.response.CommonResult;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
public interface StudentService extends IService<Student> {
    CommonResult chooseBed(Long id, Long bedId);
}
