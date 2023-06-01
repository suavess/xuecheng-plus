package com.suave.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

/**
 * @author Suave
 * @since 2023/05/30 22:22
 */
@Data
@ToString
@Schema(title = "AddCourseDTO", description = "新增课程基本信息")
public class AddCourseDTO {

    @NotEmpty(message = "课程名称不能为空")
    @Schema(title = "课程名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotEmpty(message = "适用人群不能为空")
    @Size(message = "适用人群内容过少", min = 10)
    @Schema(title = "适用人群", requiredMode = Schema.RequiredMode.REQUIRED)
    private String users;

    @Schema(title = "课程标签")
    private String tags;

    @NotEmpty(message = "课程分类不能为空")
    @Schema(title = "大分类", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mt;

    @NotEmpty(message = "课程分类不能为空")
    @Schema(title = "小分类", requiredMode = Schema.RequiredMode.REQUIRED)
    private String st;

    @NotEmpty(message = "课程等级不能为空")
    @Schema(title = "课程等级", requiredMode = Schema.RequiredMode.REQUIRED)
    private String grade;

    @Schema(title = "教学模式（普通，录播，直播等）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String teachmode;

    @Schema(title = "课程介绍")
    private String description;

    @Schema(title = "课程图片", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pic;

    @NotEmpty(message = "收费规则不能为空")
    @Schema(title = "收费规则，对应数据字典", requiredMode = Schema.RequiredMode.REQUIRED)
    private String charge;

    @Schema(title = "价格")
    private Float price;
    @Schema(title = "原价")
    private Float originalPrice;


    @Schema(title = "qq")
    private String qq;

    @Schema(title = "微信")
    private String wechat;
    @Schema(title = "电话")
    private String phone;

    @Schema(title = "有效期")
    private Integer validDays;
}
