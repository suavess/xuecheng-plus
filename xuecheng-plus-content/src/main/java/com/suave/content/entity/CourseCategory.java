package com.suave.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 课程分类
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Getter
@Setter
@TableName("course_category")
@Schema(title = "CourseCategory对象", description = "课程分类")
public class CourseCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键")
    private String id;

    @Schema(title = "分类名称")
    private String name;

    @Schema(title = "分类标签默认和名称一样")
    private String label;

    @Schema(title = "父结点id（第一级的父节点是0，自关联字段id）")
    private String parentid;

    @Schema(title = "是否显示")
    private Byte isShow;

    @Schema(title = "排序字段")
    private Integer orderby;

    @Schema(title = "是否叶子")
    private Byte isLeaf;
}
