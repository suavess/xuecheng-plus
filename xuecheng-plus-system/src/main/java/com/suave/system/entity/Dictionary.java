package com.suave.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author Suave
 * @since 2023-05-29
 */
@Getter
@Setter
@TableName("dictionary")
@Schema(title = "Dictionary对象", description = "数据字典")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "id标识")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(title = "数据字典名称")
    private String name;

    @Schema(title = "数据字典代码")
    private String code;

    @Schema(title = "数据字典项--json格式")
    private String itemValues;
}
