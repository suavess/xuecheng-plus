package com.suave.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suave.content.entity.CourseTeacher;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程-教师关系表 Mapper 接口
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Mapper
public interface CourseTeacherMapper extends BaseMapper<CourseTeacher> {

}
