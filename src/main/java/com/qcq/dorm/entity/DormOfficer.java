package com.qcq.dorm.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@EqualsAndHashCode(callSuper = true)
public class DormOfficer extends Model<DormOfficer> {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    @NotNull(message = "工号不能为空")
    private Long id;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;
    /**
     * 管理楼栋id
     */
    @TableField("building_id")
    @NotNull(message = "必须选择管理楼栋")
    private Long buildingId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
