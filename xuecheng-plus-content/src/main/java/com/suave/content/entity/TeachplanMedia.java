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
@TableName("teachplan_media")
@Schema(title = "TeachplanMedia对象", description = "")
public class TeachplanMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(title = "媒资文件id")
    private String mediaId;

    @Schema(title = "课程计划标识")
    private Long teachplanId;

    @Schema(title = "课程标识")
    private Long courseId;

    @Schema(title = "媒资文件原始名称")
    private String mediaFilename;

    private LocalDateTime createDate;

    @Schema(title = "创建人")
    private String createPeople;

    @Schema(title = "修改人")
    private String changePeople;
}
