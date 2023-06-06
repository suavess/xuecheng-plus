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
 * 课程计划
 * </p>
 *
 * @author Suave
 * @since 2023-06-01
 */
@Getter
@Setter
@TableName("teachplan")
@Schema(title = "Teachplan对象", description = "课程计划")
public class Teachplan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(title = "课程计划名称")
    private String pname;

    @Schema(title = "课程计划父级Id")
    private Long parentid;

    @Schema(title = "层级，分为1、2、3级")
    private Integer grade;

    @Schema(title = "课程类型:1视频、2文档")
    private String mediaType;

    @Schema(title = "开始直播时间")
    private LocalDateTime startTime;

    @Schema(title = "直播结束时间")
    private LocalDateTime endTime;

    @Schema(title = "章节及课程时介绍")
    private String description;

    @Schema(title = "时长，单位时:分:秒")
    private String timelength;

    @Schema(title = "排序字段")
    private Long orderby;

    @Schema(title = "课程标识")
    private Long courseId;

    @Schema(title = "课程发布标识")
    private Long coursePubId;

    @Schema(title = "状态（1正常  0删除）")
    private Integer status;

    @Schema(title = "是否支持试学或预览（试看）")
    private String isPreview;

    @Schema(title = "创建时间")
    private LocalDateTime createDate;

    @Schema(title = "修改时间")
    private LocalDateTime changeDate;
}
