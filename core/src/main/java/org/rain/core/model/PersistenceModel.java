package org.rain.core.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 数据库表通用字段
 */
@Data
public class PersistenceModel {

    /**主键id*/
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**创建时间*/
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**创建人*/
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**更新人*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**创建者所属部门*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String departCode;

    /**数据备注*/
    private String remark;
}
