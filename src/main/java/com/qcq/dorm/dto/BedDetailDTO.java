package com.qcq.dorm.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author O
 * @since 2020/2/21
 */
@Data
@Builder
public class BedDetailDTO {
    Long id;
    Integer number;
    Boolean isSelected;
    Boolean isMoveIn;
    String ownerName;
    Boolean isSmoke;
    Boolean isEarlySleep;
    String habit;
}
