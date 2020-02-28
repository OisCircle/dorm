package com.qcq.dorm.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    @NotNull(message = "学号不能为空")
    private Long id;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;
    /**
     * name
     */
    private String name;
    /**
     * 是否抽烟
     */
    @TableField("is_smoke")
    private Integer isSmoke;
    /**
     * 是否早睡
     */
    @TableField("is_early_sleep")
    private Integer isEarlySleep;
    /**
     * 兴趣爱好
     */
    private String habit;
    /**
     * 床位id
     */
    @TableField("bed_id")
    private Long bedId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
