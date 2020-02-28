package com.qcq.dorm.service;

import com.baomidou.mybatisplus.service.IService;
import com.qcq.dorm.entity.FixForm;

import java.util.List;

/**
 * <p>
 * 维修工单 服务类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
public interface FixFormService extends IService<FixForm> {
    List<FixForm> getDormFixForms(Long dormId);

    List<FixForm> getBuildingFixForms(Long buildingId);
}
