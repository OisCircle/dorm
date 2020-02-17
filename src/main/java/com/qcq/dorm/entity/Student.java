package com.qcq.dorm.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    private Long id;
    /**
     * 密码
     */
    private String password;
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
