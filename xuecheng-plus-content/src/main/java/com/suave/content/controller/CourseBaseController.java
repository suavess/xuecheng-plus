package com.suave.content.controller;

import com.suave.base.dto.PageDTO;
import com.suave.base.vo.PageVO;
import com.suave.content.dto.AddCourseDTO;
import com.suave.content.dto.QueryCourseDTO;
import com.suave.content.entity.CourseBase;
import com.suave.content.service.ICourseBaseService;
import com.suave.content.vo.AddCourseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程基本信息 前端控制器
 * </p>
 *
 * @author Suave
 * @since 2023-05-28
 */
@RestController
@Tag(name = "课程信息接口")
public class CourseBaseController {

    @Autowired
    private ICourseBaseService courseBaseService;

    @Operation(summary = "课程查询接口")
    @PostMapping("/course/list")
    public PageVO<CourseBase> list(PageDTO pageDTO, @RequestBody QueryCourseDTO queryCourseDTO) {
        return courseBaseService.queryCourseBaseList(pageDTO, queryCourseDTO);
    }

    @Operation(summary = "新增课程")
    @PostMapping("/course")
    public AddCourseVO add(@RequestBody @Valid AddCourseDTO addCourseDTO) {
        Long companyId = 1232141425L;
        return courseBaseService.createCourseBase(companyId, addCourseDTO);
    }

}
