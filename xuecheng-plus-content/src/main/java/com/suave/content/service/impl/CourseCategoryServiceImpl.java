package com.suave.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.content.entity.CourseCategory;
import com.suave.content.mapper.CourseCategoryMapper;
import com.suave.content.service.ICourseCategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程分类 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements ICourseCategoryService {

}
