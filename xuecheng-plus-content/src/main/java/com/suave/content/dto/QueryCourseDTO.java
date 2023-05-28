package com.suave.content.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author Suave
 * @since 2023/05/28 16:26
 */
@Data
@ToString
public class QueryCourseDTO {
    /**
     * 审核状态
     */
    private String auditStatus;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 发布状态
     */
    private String publishStatus;
}
