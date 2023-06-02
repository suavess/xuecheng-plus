package com.suave.content.controller;

import cn.hutool.core.lang.tree.Tree;
import com.suave.content.service.ITeachplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RestController
@RequestMapping("/teachplan")
public class TeachplanController {

    @Autowired
    private ITeachplanService teachplanService;

    @GetMapping("/{courseId}/tree-nodes")
    public List<Tree<Long>> getTreeNodes(@PathVariable Long courseId) {
        return teachplanService.getTreeNodes(courseId);
    }

}
