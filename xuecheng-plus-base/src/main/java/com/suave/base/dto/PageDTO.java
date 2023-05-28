package com.suave.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Suave
 * @since 2023/05/28 15:52
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    @Schema(title = "页码")
    private Long pageNo = 1L;

    @Schema(title = "每页数据量")
    private Long pageSize = 10L;
}
