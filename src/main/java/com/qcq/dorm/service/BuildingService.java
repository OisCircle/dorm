package com.qcq.dorm.service;

import com.qcq.dorm.entity.Building;
import com.baomidou.mybatisplus.service.IService;
import com.qcq.dorm.entity.Student;

import java.util.List;

/**
 * <p>
 * 楼栋 服务类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
public interface BuildingService extends IService<Building> {
    List<Student> getStudents(Long buildingId);
}
