package com.suave.content.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.content.entity.CourseCategory;
import com.suave.content.mapper.CourseCategoryMapper;
import com.suave.content.service.ICourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程分类 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Slf4j
@Service("courseCategoryService")
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements ICourseCategoryService {
    @Override
    public List<Tree<String>> queryTreeNodes(String id) {
        List<CourseCategory> courseCategoryList = baseMapper.queryTreeNodes(id);
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setParentIdKey("parentid");
        treeNodeConfig.setChildrenKey("childrenTreeNodes");
        treeNodeConfig.setWeightKey("orderby");
        return TreeUtil.build(courseCategoryList, "1", treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentid());
            tree.setWeight(treeNode.getOrderby());
            tree.setName(treeNode.getName());
            tree.putExtra("label", treeNode.getName());
        });
    }
}
