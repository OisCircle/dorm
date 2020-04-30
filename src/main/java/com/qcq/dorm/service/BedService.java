package com.qcq.dorm.service;

import com.qcq.dorm.entity.Bed;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 床位 服务类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
public interface BedService extends IService<Bed> {
    Bed selectByIdForUpdate(Long id);
}
