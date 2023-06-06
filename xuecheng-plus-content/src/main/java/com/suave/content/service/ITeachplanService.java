package com.suave.content.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suave.content.dto.SaveTeachPlanDTO;
import com.suave.content.entity.Teachplan;

import java.util.List;

/**
 * <p>
 * 课程计划 服务类
 * </p>
 *
 * @author Suave
 * @since 2023-06-01
 */
public interface ITeachplanService extends IService<Teachplan> {

    List<Tree<Long>> getTreeNodes(Long courseId);

    void saveTeachPlan(SaveTeachPlanDTO saveTeachPlanDTO);
}
