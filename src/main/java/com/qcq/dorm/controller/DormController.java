package com.qcq.dorm.controller;


import com.qcq.dorm.dto.DormSelectionDTO;
import com.qcq.dorm.entity.Dorm;
import com.qcq.dorm.response.CommonResult;
import com.qcq.dorm.service.DormService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 宿舍 前端控制器
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/dorm")
public class DormController {
    @Resource
    private DormService dormService;
    @PutMapping("/updateSelectDorm")
    CommonResult updateSelectDorm(Boolean isOpen, String endTime) {
        final DormSelectionDTO oldDto = dormService.getSelectionStatus();
        if (endTime == null) {
            endTime = oldDto.getEndTime();
        } else {
            try {
                LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                return CommonResult.failure("截止时间格式不正确");
            }
        }
        return CommonResult.expect(dormService.updateSelectionStatus(isOpen, endTime));
    }

    @GetMapping("/getDormSelectStatus")
    CommonResult getDormSelectStatus() {
        return CommonResult.success(dormService.getSelectionStatus());
    }

    @PutMapping("/changeCleanScore")
    CommonResult changeCleanScore(Long dormId, Integer cleanScore) {
        if (dormId == null || cleanScore == null || dormId < 0 || cleanScore < 0 || cleanScore > 10) {
            return CommonResult.failure("参数错误");
        }
        final Dorm dorm = new Dorm();
        dorm.setId(dormId);
        dorm.setCleanScore(cleanScore);
        return CommonResult.expect(dormService.updateById(dorm));
    }
}

