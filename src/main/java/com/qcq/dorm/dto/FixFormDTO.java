package com.qcq.dorm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author O
 * @since 2020/2/22
 */
@Data
@Builder
public class FixFormDTO {
    private Long id;
    private String itemName;
    private String desc;
    private LocalDateTime submitTime;
    private LocalDateTime finishTime;
    private Integer state;
    private Integer satisfaction;
    private Long dormNumber;
    /**
     * 配合前端展示
     * 0-展示“-”
     * 1-展示评价按钮
     * 2-展示评价
     */
    private Integer satisfactionTag;
}
