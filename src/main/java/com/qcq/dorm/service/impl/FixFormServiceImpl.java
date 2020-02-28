package com.qcq.dorm.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qcq.dorm.entity.Dorm;
import com.qcq.dorm.entity.FixForm;
import com.qcq.dorm.entity.Item;
import com.qcq.dorm.entity.Student;
import com.qcq.dorm.mapper.FixFormMapper;
import com.qcq.dorm.service.BuildingService;
import com.qcq.dorm.service.DormService;
import com.qcq.dorm.service.FixFormService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qcq.dorm.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 维修工单 服务实现类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Service
public class FixFormServiceImpl extends ServiceImpl<FixFormMapper, FixForm> implements FixFormService {
    @Resource
    private ItemService itemService;
    @Resource
    private DormService dormService;
    @Override
    public List<FixForm> getBuildingFixForms(Long buildingId) {
        final List<Dorm> dorms = dormService.getDorms(buildingId);
        List<FixForm> fixForms = new ArrayList<>();
        dorms.forEach(dorm -> {
            final List<FixForm> item = getDormFixForms(dorm.getId());
            fixForms.addAll(item);
        });
        return fixForms;
    }

    @Override
    public List<FixForm> getDormFixForms(Long dormId) {
        final EntityWrapper<Item> itemWrapper = new EntityWrapper<>();
        itemWrapper.eq("dorm_id", dormId);
        final List<Item> items = itemService.selectList(itemWrapper);
        final EntityWrapper<FixForm> fixFormWrapper = new EntityWrapper<>();
        if (items.size() < 1) {
            return new ArrayList<>();
        } else {
            fixFormWrapper.in("item_id", items.stream().map(Item::getId).collect(Collectors.toList()));
        }
        return selectList(fixFormWrapper);
    }
}
