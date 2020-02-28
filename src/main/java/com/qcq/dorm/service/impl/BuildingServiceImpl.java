package com.qcq.dorm.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qcq.dorm.entity.Building;
import com.qcq.dorm.entity.Dorm;
import com.qcq.dorm.entity.Student;
import com.qcq.dorm.mapper.BuildingMapper;
import com.qcq.dorm.service.BuildingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qcq.dorm.service.DormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 楼栋 服务实现类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {
    @Resource
    private DormService dormService;
    @Override
    public List<Student> getStudents(Long buildingId) {
        final EntityWrapper<Dorm> dormWrapper = new EntityWrapper<>();
        dormWrapper.eq("building_id", buildingId);
        final List<Dorm> dorms = dormService.selectList(dormWrapper);
        List<Student> students = new ArrayList<>();
        dorms.forEach(dorm->{
            final List<Student> list = dormService.getStudents(dorm.getId());
            students.addAll(list);
        });
        return students;
    }
}
