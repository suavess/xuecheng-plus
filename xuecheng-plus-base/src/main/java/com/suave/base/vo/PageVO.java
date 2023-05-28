package com.suave.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Suave
 * @since 2023/05/28 16:11
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "分页回参")
public class PageVO<T> implements Serializable {
    /**
     * 数据列表
     */
    @Schema(title = "数据列表")
    private List<T> items;

    /**
     * 总记录数
     */
    @Schema(title = "总记录数")
    private Long counts;

    /**
     * 当前页码
     */
    @Schema(title = "当前页码")
    private Long page;

    /**
     * 每页记录数
     */
    @Schema(title = "每页记录数")
    private Long pageSize;
}
