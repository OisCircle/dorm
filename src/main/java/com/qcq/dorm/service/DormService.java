package com.qcq.dorm.service;

import com.qcq.dorm.dto.DormSelectionDTO;
import com.qcq.dorm.entity.Dorm;
import com.baomidou.mybatisplus.service.IService;
import com.qcq.dorm.entity.Student;

import java.util.List;

/**
 * <p>
 * 宿舍 服务类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
public interface DormService extends IService<Dorm> {
    boolean updateSelectionStatus(Boolean isOpen, String endTime);

    DormSelectionDTO getSelectionStatus();

    List<Student> getStudents(Long dormId);

    List<Dorm> getDorms(Long buildingId);
}
