package com.suave.content.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Getter
@Setter
@TableName("course_audit")
@Schema(title = "CourseAudit对象")
public class CourseAudit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(title = "课程id")
    private Long courseId;

    @Schema(title = "审核意见")
    private String auditMind;

    @Schema(title = "审核状态")
    private String auditStatus;

    @Schema(title = "审核人")
    private String auditPeople;

    @Schema(title = "审核时间")
    private LocalDateTime auditDate;
}
