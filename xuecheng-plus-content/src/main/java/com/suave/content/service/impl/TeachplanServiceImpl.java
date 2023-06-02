package com.suave.content.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.content.entity.Teachplan;
import com.suave.content.mapper.TeachplanMapper;
import com.suave.content.service.ITeachplanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程计划 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2023-06-01
 */
@Tag(name = "教学计划接口")
@Service("teachplanService")
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements ITeachplanService {
    @Override
    public List<Tree<Long>> getTreeNodes(Long courseId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId, courseId);
        List<Teachplan> teachplans = baseMapper.selectList(queryWrapper);
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("pname");
        treeNodeConfig.setParentIdKey("parentid");
        treeNodeConfig.setChildrenKey("teachPlanTreeNodes");
        treeNodeConfig.setWeightKey("orderby");
        return TreeUtil.build(teachplans, 0L, treeNodeConfig, (obj, treeNode) -> {
            treeNode.setId(obj.getId());
            treeNode.setParentId(obj.getParentid());
            treeNode.setWeight(obj.getOrderby());
            treeNode.putAll(BeanUtil.beanToMap(obj));
        });
    }
}
