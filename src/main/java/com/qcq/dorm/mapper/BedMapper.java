package com.qcq.dorm.mapper;

import com.qcq.dorm.entity.Bed;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 床位 Mapper 接口
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
public interface BedMapper extends BaseMapper<Bed> {
    Bed selectByIdForUpdate(@Param("id") Long id);
}
