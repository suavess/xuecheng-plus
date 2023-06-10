package com.suave.media.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author Suave
 * @since 2023/06/10 16:16
 */
@Data
@ToString
public class UploadFileDTO {
    /**
     * 文件名称
     */
    private String filename;


    /**
     * 文件类型（文档，音频，视频）
     */
    private String fileType;
    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 标签
     */
    private String tags;

    /**
     * 上传人
     */
    private String username;

    /**
     * 备注
     */
    private String remark;
}
