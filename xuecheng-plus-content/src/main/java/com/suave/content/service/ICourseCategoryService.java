package com.suave.content.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suave.content.entity.CourseCategory;

import java.util.List;

/**
 * <p>
 * 课程分类 服务类
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
public interface ICourseCategoryService extends IService<CourseCategory> {

    /**
     * 树状结构，查询课程分类列表
     *
     * @return -
     */
    List<Tree<String>> queryTreeNodes(String id);

}
