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
 * 宿舍物品
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Data
@Accessors(chain = true)
public class Item extends Model<Item> {

    private static final long serialVersionUID = 1L;

    /**
     * 物品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 宿舍id
     */
    @TableField("dorm_id")
    private Long dormId;
    /**
     * 物品名称
     */
    private String name;
    /**
     * 是否损坏
     */
    @TableField("is_broken")
    private Integer isBroken;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
