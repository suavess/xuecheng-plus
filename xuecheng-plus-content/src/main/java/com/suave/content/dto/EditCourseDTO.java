package com.suave.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

/**
 * @author Suave
 * @since 2023/06/02 16:50
 */
@Data
@ToString
@Schema(title = "EditCourseDTO", description = "修改课程基本信息")
public class EditCourseDTO extends AddCourseDTO {
    @NotNull(message = "课程id不能为空")
    @Schema(title = "课程id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}

