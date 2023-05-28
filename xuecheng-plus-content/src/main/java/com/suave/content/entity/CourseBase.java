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
 * 课程基本信息
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Getter
@Setter
@TableName("course_base")
@Schema(title = "CouseBaser对象", description = "课程基本信息")
public class CourseBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(title = "机构ID")
    private Long companyId;

    @Schema(title = "机构名称")
    private String companyName;

    @Schema(title = "课程名称")
    private String name;

    @Schema(title = "适用人群")
    private String users;

    @Schema(title = "课程标签")
    private String tags;

    @Schema(title = "大分类")
    private String mt;

    @Schema(title = "小分类")
    private String st;

    @Schema(title = "课程等级")
    private String grade;

    @Schema(title = "教育模式(common普通，record 录播，live直播等）")
    private String teachmode;

    @Schema(title = "课程介绍")
    private String description;

    @Schema(title = "课程图片")
    private String pic;

    @Schema(title = "创建时间")
    private LocalDateTime createDate;

    @Schema(title = "修改时间")
    private LocalDateTime changeDate;

    @Schema(title = "创建人")
    private String createPeople;

    @Schema(title = "更新人")
    private String changePeople;

    @Schema(title = "审核状态")
    private String auditStatus;

    @Schema(title = "课程发布状态 未发布  已发布 下线")
    private String status;
}
