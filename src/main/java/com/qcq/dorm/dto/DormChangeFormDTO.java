package com.qcq.dorm.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author O
 * @since 2020/2/22
 */
@Data
@Builder
public class DormChangeFormDTO {
    private Long id;
    private String name;
    private String oldLocation;
    private String newLocation;
    private String reason;
    private Integer state;
}
