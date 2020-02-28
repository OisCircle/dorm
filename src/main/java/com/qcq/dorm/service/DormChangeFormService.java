package com.qcq.dorm.service;

import com.baomidou.mybatisplus.service.IService;
import com.qcq.dorm.entity.DormChangeForm;

import java.util.List;

/**
 * <p>
 * 更换宿舍工单 服务类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
public interface DormChangeFormService extends IService<DormChangeForm> {
    Integer getDormChangeFormCount(Long stuId);

    List<DormChangeForm> getDormChangeForms(Long buildingId);
}
