package com.qcq.dorm.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 宿管
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Data
@Accessors(chain = true)
@TableName("dorm_officer")
public class DormOfficer extends Model<DormOfficer> {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    private Long id;
    /**
     * 密码
     */
    private String password;
    /**
     * 管理楼栋id
     */
    @TableField("building_id")
    private Long buildingId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
