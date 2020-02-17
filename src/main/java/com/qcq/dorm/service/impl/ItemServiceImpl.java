package com.qcq.dorm.service.impl;

import com.qcq.dorm.entity.Item;
import com.qcq.dorm.mapper.ItemMapper;
import com.qcq.dorm.service.ItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 宿舍物品 服务实现类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}
