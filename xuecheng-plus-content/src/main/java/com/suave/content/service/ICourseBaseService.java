package com.suave.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suave.base.dto.PageDTO;
import com.suave.base.vo.PageVO;
import com.suave.content.dto.QueryCourseDTO;
import com.suave.content.entity.CourseBase;

/**
 * <p>
 * 课程基本信息 服务类
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
public interface ICourseBaseService extends IService<CourseBase> {
    /**
     * 课程分页查询
     *
     * @param pageDTO        分页条件
     * @param queryCourseDTO 查询条件
     * @return 返回结果
     */
    PageVO<CourseBase> queryCourseBaseList(PageDTO pageDTO, QueryCourseDTO queryCourseDTO);
}
