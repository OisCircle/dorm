package com.qcq.dorm.service.impl;

import com.qcq.dorm.entity.Student;
import com.qcq.dorm.mapper.StudentMapper;
import com.qcq.dorm.service.StudentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
