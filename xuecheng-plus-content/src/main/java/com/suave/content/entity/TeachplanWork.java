package com.suave.content.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Suave
 * @since 2023-06-01
 */
@Getter
@Setter
@TableName("teachplan_work")
@Schema(title = "TeachplanWork对象", description = "")
public class TeachplanWork implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(title = "作业信息标识")
    private Long workId;

    @Schema(title = "作业标题")
    private String workTitle;

    @Schema(title = "课程计划标识")
    private Long teachplanId;

    @Schema(title = "课程标识")
    private Long courseId;

    private LocalDateTime createDate;

    private Long coursePubId;
}
