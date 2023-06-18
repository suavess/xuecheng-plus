package com.suave.content.controller;

import cn.hutool.core.lang.tree.Tree;
import com.suave.content.dto.BindTeachPlanMediaDTO;
import com.suave.content.dto.SaveTeachPlanDTO;
import com.suave.content.service.ITeachplanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程计划 前端控制器
 * </p>
 *
 * @author Suave
 * @since 2023-06-01
 */
@Tag(name = "课程计划接口")
@RestController
@RequestMapping("/teachplan")
public class TeachplanController {

    @Autowired
    private ITeachplanService teachplanService;

    @Operation(summary = "课程计划查询接口")
    @GetMapping("/{courseId}/tree-nodes")
    public List<Tree<Long>> getTreeNodes(@PathVariable Long courseId) {
        return teachplanService.getTreeNodes(courseId);
    }

    @Operation(summary = "课程计划新增或修改")
    @PostMapping
    public void saveTeachPlan(@RequestBody SaveTeachPlanDTO saveTeachPlanDTO) {
        teachplanService.saveTeachPlan(saveTeachPlanDTO);
    }

    @Operation(summary = "课程计划和媒资信息绑定")
    @PostMapping("/association/media")
    public void associationMedia(@RequestBody BindTeachPlanMediaDTO bindTeachPlanMediaDTO) {
        teachplanService.associationMedia(bindTeachPlanMediaDTO);
    }

}
