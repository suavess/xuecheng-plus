package com.suave.content.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.content.dto.SaveTeachPlanDTO;
import com.suave.content.entity.Teachplan;
import com.suave.content.mapper.TeachplanMapper;
import com.suave.content.service.ITeachplanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTeachPlan(SaveTeachPlanDTO saveTeachPlanDTO) {

        // 课程计划id
        Long id = saveTeachPlanDTO.getId();
        // 修改课程计划
        if (id != null) {
            Teachplan teachplan = baseMapper.selectById(id);
            BeanUtils.copyProperties(saveTeachPlanDTO, teachplan);
            baseMapper.updateById(teachplan);
        } else {
            // 取出同父同级别的课程计划数量
            long count = getTeachplanCount(saveTeachPlanDTO.getCourseId(), saveTeachPlanDTO.getParentid());
            Teachplan teachplanNew = new Teachplan();
            // 设置排序号
            teachplanNew.setOrderby(count + 1);
            BeanUtils.copyProperties(saveTeachPlanDTO, teachplanNew);
            baseMapper.insert(teachplanNew);
        }
    }

    /**
     * @param courseId 课程id
     * @param parentId 父课程计划id
     * @return int 最新排序号
     * @description 获取最新的排序号
     * @author Mr.M
     * @date 2022/9/9 13:43
     */
    private long getTeachplanCount(long courseId, long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId, courseId);
        queryWrapper.eq(Teachplan::getParentid, parentId);
        return baseMapper.selectCount(queryWrapper);
    }
}
