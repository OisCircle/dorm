package com.qcq.dorm.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qcq.dorm.dto.DormSelectionDTO;
import com.qcq.dorm.entity.Bed;
import com.qcq.dorm.entity.Dorm;
import com.qcq.dorm.entity.Student;
import com.qcq.dorm.mapper.DormMapper;
import com.qcq.dorm.redis.RedisCacheSupport;
import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.BedService;
import com.qcq.dorm.service.DormService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qcq.dorm.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 宿舍 服务实现类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Service
public class DormServiceImpl extends ServiceImpl<DormMapper, Dorm> implements DormService {
    private static final String SELECTION_KEY = "dorm-select";
    @Resource
    private BedService bedService;
    @Resource
    private StudentService studentService;
    @Resource
    private RedisCacheSupport<DormSelectionDTO> cache;

    @Override
    public boolean updateSelectionStatus(Boolean isOpen, String endTime) {
        final DormSelectionDTO dto = DormSelectionDTO.builder()
                .isOpen(isOpen)
                .endTime(endTime)
                .build();
        cache.set(SELECTION_KEY, dto);
        return true;
    }

    @Override
    public List<Dorm> getDorms(Long buildingId) {
        final EntityWrapper<Dorm> wrapper = new EntityWrapper<>();
        wrapper.eq("building_id", buildingId);
        return selectList(wrapper);
    }

    @Override
    public DormSelectionDTO getSelectionStatus() {
        return cache.get(SELECTION_KEY);
    }

    @Override
    public List<Student> getStudents(Long dormId) {
        final EntityWrapper<Bed> wrapper = new EntityWrapper<>();
        wrapper.eq("dorm_id", dormId);
        final List<Bed> beds = bedService.selectList(wrapper);
        if (beds.size() < 1) {
            return new ArrayList<>();
        }
        final List<Long> ids = beds.stream().map(Bed::getId).collect(Collectors.toList());
        final EntityWrapper<Student> stuWrapper = new EntityWrapper<>();
        stuWrapper.in("bed_id", ids);
        return studentService.selectList(stuWrapper);
    }
}
