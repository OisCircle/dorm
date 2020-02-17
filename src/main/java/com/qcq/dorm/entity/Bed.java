package com.qcq.dorm.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 床位
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Bed extends Model<Bed> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 宿舍id
     */
    @TableField("dorm_id")
    private Long dormId;
    /**
     * 床号
     */
    private Integer number;
    /**
     * 是否被选择
     */
    @TableField("is_selected")
    private Boolean isSelected;
    /**
     * 是否入住
     */
    @TableField("is_move_in")
    private Boolean isMoveIn;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
