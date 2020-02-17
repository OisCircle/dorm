package com.qcq.dorm.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 维修工单
 * </p>
 *
 * @author O
 * @since 2020-02-17
 */
@Data
@Accessors(chain = true)
@TableName("fix_form")
@EqualsAndHashCode(callSuper = true)
public class FixForm extends Model<FixForm> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 物品id
     */
    @TableField("item_id")
    private Long itemId;
    /**
     * 提交时间
     */
    @TableField("submit_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime submitTime;
    /**
     * 维修完成时间
     */
    @TableField("finish_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime finishTime;
    /**
     * 状态
     * 0-已提交
     * 1-维修中
     * 2-已完成
     */
    private Integer state;
    /**
     * 满意度(1-10)
     */
    private Integer satisfaction;
    /**
     * 评论
     */
    private String comment;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
