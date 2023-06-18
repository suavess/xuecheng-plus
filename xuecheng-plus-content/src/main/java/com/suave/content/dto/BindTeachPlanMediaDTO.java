package com.suave.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * @author Suave
 * @since 2023/06/18 20:55
 */
@Data
@Tag(name = "BindTeachPlanMediaDTO", description = "教学计划-媒资绑定提交数据")
public class BindTeachPlanMediaDTO {

    @Schema(title = "媒资文件id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mediaId;

    @Schema(title = "媒资文件名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileName;

    @Schema(title = "课程计划标识", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long teachplanId;


}
