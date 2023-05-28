package com.suave.content.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.base.dto.PageDTO;
import com.suave.base.vo.PageVO;
import com.suave.content.dto.QueryCourseDTO;
import com.suave.content.entity.CourseBase;
import com.suave.content.mapper.CourseBaseMapper;
import com.suave.content.service.ICourseBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程基本信息 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Slf4j
@Service("courseBaseService")
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements ICourseBaseService {
    @Override
    public PageVO<CourseBase> queryCourseBaseList(PageDTO pageDTO, QueryCourseDTO queryCourseDTO) {
        LambdaQueryWrapper<CourseBase> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StrUtil.isNotEmpty(queryCourseDTO.getAuditStatus()), CourseBase::getAuditStatus, queryCourseDTO.getAuditStatus());
        lqw.like(StrUtil.isNotEmpty(queryCourseDTO.getCourseName()), CourseBase::getName, queryCourseDTO.getCourseName());
        lqw.like(StrUtil.isNotEmpty(queryCourseDTO.getPublishStatus()), CourseBase::getStatus, queryCourseDTO.getPublishStatus());
        Page<CourseBase> p = new Page<>(pageDTO.getPageNo(), pageDTO.getPageSize());
        Page<CourseBase> pageResult = baseMapper.selectPage(p, lqw);
        PageVO<CourseBase> result = new PageVO<>();
        result.setCounts(pageResult.getTotal());
        result.setPage(pageResult.getCurrent());
        result.setPageSize(pageResult.getSize());
        result.setItems(pageResult.getRecords());
        return result;
    }
}
