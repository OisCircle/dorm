package com.qcq.dorm.service;

import com.qcq.dorm.entity.Item;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 宿舍物品 服务类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
public interface ItemService extends IService<Item> {
    List<Item> getItem(Long dormId);
}
