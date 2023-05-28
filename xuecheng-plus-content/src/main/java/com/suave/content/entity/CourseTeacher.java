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
 * 课程-教师关系表
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Getter
@Setter
@TableName("course_teacher")
@Schema(title = "CourseTeacher对象", description = "课程-教师关系表")
public class CourseTeacher implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(title = "课程标识")
    private Long courseId;

    @Schema(title = "教师标识")
    private String teacherName;

    @Schema(title = "教师职位")
    private String position;

    @Schema(title = "教师简介")
    private String introduction;

    @Schema(title = "照片")
    private String photograph;

    @Schema(title = "创建时间")
    private LocalDateTime createDate;
}
