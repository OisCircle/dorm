package com.qcq.dorm.service.impl;

import com.qcq.dorm.entity.Bed;
import com.qcq.dorm.entity.Student;
import com.qcq.dorm.mapper.StudentMapper;
import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.BedService;
import com.qcq.dorm.service.StudentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Resource
    private BedService bedService;

    @Override
    @Transactional
    public CommonResult chooseBed(Long id, Long bedId) {
        final Student student = selectById(id);
        final Long oldBedId = student.getBedId();
        student.setBedId(bedId);
        System.out.println("线程 " + Thread.currentThread().getName() + " 开始竞争锁");
        final Bed newBed = bedService.selectByIdForUpdate(bedId);
        final Bed oldBed = bedService.selectByIdForUpdate(oldBedId);
        System.out.println("线程 " + Thread.currentThread().getName() + " 获取到锁");
        if (newBed.getIsSelected() == 0 && student.updateById()) {
            oldBed.setIsSelected(0);
            oldBed.setIsMoveIn(0);
            newBed.setIsSelected(1);
            newBed.setIsMoveIn(0);
            System.out.println("线程 " + Thread.currentThread().getName() + " 开始更新数据");
            return CommonResult.success(newBed.updateById() && oldBed.updateById());
        } else {
            System.out.println("线程 " + Thread.currentThread().getName() + " 竞争失败");
            return CommonResult.failure("床位已经被选择，请重新选择");
        }
    }
}
