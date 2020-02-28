package com.qcq.dorm.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
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
 * 更换宿舍工单
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Data
@Accessors(chain = true)
@TableName("dorm_change_form")
@EqualsAndHashCode(callSuper = true)
public class DormChangeForm extends Model<DormChangeForm> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("student_id")
    @NotNull(message = "没有学生id")
    private Long studentId;
    /**
     * 原床号
     */
    @TableField("old_bed_id")
    private Long oldBedId;
    /**
     * 新床号
     */
    @TableField("new_bed_id")
    @NotNull(message = "没有选择新床位")
    private Long newBedId;
    /**
     * 原因
     */
    @NotEmpty(message = "原因不能为空")
    private String reason;
    /**
     * 状态
     * 0-已提交
     * 1-通过
     * 2-拒绝
     */
    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
