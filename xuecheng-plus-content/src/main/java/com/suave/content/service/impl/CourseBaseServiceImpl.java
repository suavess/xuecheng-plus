package com.suave.content.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.base.dto.PageDTO;
import com.suave.base.vo.PageVO;
import com.suave.content.dto.AddCourseDTO;
import com.suave.content.dto.QueryCourseDTO;
import com.suave.content.entity.CourseBase;
import com.suave.content.entity.CourseMarket;
import com.suave.content.mapper.CourseBaseMapper;
import com.suave.content.mapper.CourseMarketMapper;
import com.suave.content.service.ICourseBaseService;
import com.suave.content.vo.AddCourseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Autowired
    private CourseMarketMapper courseMarketMapper;

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

    @Override
    @Transactional
    public AddCourseVO createCourseBase(Long companyId, AddCourseDTO addCourseDTO) {
        AddCourseVO result = new AddCourseVO();
        CourseBase courseBase = BeanUtil.copyProperties(addCourseDTO, CourseBase.class);
        courseBase.setCompanyId(companyId);
        courseBase.setCreateDate(LocalDateTime.now());
        courseBase.setAuditStatus("202002");
        courseBase.setStatus("203001");
        int insert = baseMapper.insert(courseBase);
        if (insert < 0) {
            throw new RuntimeException("添加课程失败");
        }
        //
        CourseMarket courseMarket = new CourseMarket();
        BeanUtil.copyProperties(addCourseDTO, courseMarket);
        courseMarket.setId(courseBase.getId());
        saveCourseMarket(courseMarket);
        //
        BeanUtil.copyProperties(courseBase, result);
        BeanUtil.copyProperties(courseMarket, result);
        return result;

    }

    private int saveCourseMarket(CourseMarket courseMarket) {
        CourseMarket existEntity = courseMarketMapper.selectById(courseMarket.getId());
        if (Objects.isNull(existEntity)) {
            return courseMarketMapper.insert(courseMarket);
        } else {
            return courseMarketMapper.updateById(courseMarket);
        }
    }
}
