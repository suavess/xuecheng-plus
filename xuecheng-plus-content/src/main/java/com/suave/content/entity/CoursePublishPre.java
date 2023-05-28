package com.suave.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程发布
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Getter
@Setter
@TableName("course_publish_pre")
@Schema(title = "CoursePublishPre对象", description = "课程发布")
public class CoursePublishPre implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键")
    private Long id;

    @Schema(title = "机构ID")
    private Long companyId;

    @Schema(title = "公司名称")
    private String companyName;

    @Schema(title = "课程名称")
    private String name;

    @Schema(title = "适用人群")
    private String users;

    @Schema(title = "标签")
    private String tags;

    @Schema(title = "创建人")
    private String username;

    @Schema(title = "大分类")
    private String mt;

    @Schema(title = "大分类名称")
    private String mtName;

    @Schema(title = "小分类")
    private String st;

    @Schema(title = "小分类名称")
    private String stName;

    @Schema(title = "课程等级")
    private String grade;

    @Schema(title = "教育模式")
    private String teachmode;

    @Schema(title = "课程图片")
    private String pic;

    @Schema(title = "课程介绍")
    private String description;

    @Schema(title = "课程营销信息，json格式")
    private String market;

    @Schema(title = "所有课程计划，json格式")
    private String teachplan;

    @Schema(title = "教师信息，json格式")
    private String teachers;

    @Schema(title = "提交时间")
    private LocalDateTime createDate;

    @Schema(title = "审核时间")
    private LocalDateTime auditDate;

    @Schema(title = "状态")
    private String status;

    @Schema(title = "备注")
    private String remark;

    @Schema(title = "收费规则，对应数据字典--203")
    private String charge;

    @Schema(title = "现价")
    private Double price;

    @Schema(title = "原价")
    private Double originalPrice;

    @Schema(title = "课程有效期天数")
    private Integer validDays;
}
