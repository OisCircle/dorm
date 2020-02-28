package com.qcq.dorm.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 控制宿舍选择的开关
 *
 * @author O
 * @since 2020/2/23
 */
@Data
@Builder
public class DormSelectionDTO implements Serializable {
    private Boolean isOpen;
    private String endTime;
}
