package com.suave.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 课程营销信息
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Getter
@Setter
@TableName("course_market")
@Schema(title = "CourseMarket对象", description = "课程营销信息")
public class CourseMarket implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键，课程id")
    private Long id;

    @Schema(title = "收费规则，对应数据字典")
    private String charge;

    @Schema(title = "现价")
    private Double price;

    @Schema(title = "原价")
    private Double originalPrice;

    @Schema(title = "咨询qq")
    private String qq;

    @Schema(title = "微信")
    private String wechat;

    @Schema(title = "电话")
    private String phone;

    @Schema(title = "有效期天数")
    private Integer validDays;
}
