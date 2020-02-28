package com.qcq.dorm.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qcq.dorm.entity.DormChangeForm;
import com.qcq.dorm.entity.Student;
import com.qcq.dorm.mapper.DormChangeFormMapper;
import com.qcq.dorm.service.BuildingService;
import com.qcq.dorm.service.DormChangeFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 更换宿舍工单 服务实现类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Service
public class DormChangeFormServiceImpl extends ServiceImpl<DormChangeFormMapper, DormChangeForm> implements DormChangeFormService {
    @Resource
    private BuildingService buildingService;
    @Override
    public Integer getDormChangeFormCount(Long stuId) {
        final EntityWrapper<DormChangeForm> wrapper = new EntityWrapper<>();
        wrapper.eq("student_id", stuId);
        return selectCount(wrapper);
    }

    @Override
    public List<DormChangeForm> getDormChangeForms(Long buildingId) {
        final List<Student> students = buildingService.getStudents(buildingId);
        if (students.size() < 1) {
            return new ArrayList<>();
        }
        final EntityWrapper<DormChangeForm> wrapper = new EntityWrapper<>();
        wrapper.in("student_id", students.stream().map(Student::getId).collect(Collectors.toList()));
        return selectList(wrapper);
    }
}
