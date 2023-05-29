package com.suave.content.controller;

import cn.hutool.core.lang.tree.Tree;
import com.suave.content.service.ICourseCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程分类 前端控制器
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@RestController
@RequestMapping("course-category")
@Tag(name = "课程信息接口")
public class CourseCategoryController {

    @Autowired
    private ICourseCategoryService courseCategoryService;

    @GetMapping("/tree-nodes")
    @Operation(summary = "课程分类查询，树状结构")
    public List<Tree<String>> queryTreeNodes() {
        return courseCategoryService.queryTreeNodes("1");
    }
}
