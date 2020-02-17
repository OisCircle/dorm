package com.qcq.dorm.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 宿舍
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Data
@Accessors(chain = true)
public class Dorm extends Model<Dorm> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 所属楼栋id
     */
    @TableField("building_id")
    private Long buildingId;
    /**
     * 宿舍号
     */
    private Long number;
    /**
     * 卫生分数(0-10)
     */
    @TableField("clean_score")
    private Integer cleanScore;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
