package com.suave.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suave.content.entity.CourseCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Mapper
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

}
