package com.suave.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.content.entity.CourseTeacher;
import com.suave.content.mapper.CourseTeacherMapper;
import com.suave.content.service.ICourseTeacherService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程-教师关系表 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements ICourseTeacherService {

}
