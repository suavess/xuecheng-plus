package com.suave.media.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Suave
 * @since 2023-06-09
 */
@Getter
@Setter
@TableName("media_process")
@Schema(name = "MediaProcess对象", description = "")
public class MediaProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "文件标识")
    private String fileId;

    @Schema(name = "文件名称")
    private String filename;

    @Schema(name = "存储桶")
    private String bucket;

    @Schema(name = "存储路径")
    private String filePath;

    @Schema(name = "状态,1:未处理，2：处理成功  3处理失败")
    private String status;

    @Schema(name = "上传时间")
    private LocalDateTime createDate;

    @Schema(name = "完成时间")
    private LocalDateTime finishDate;

    @Schema(name = "媒资文件访问地址")
    private String url;

    @Schema(name = "失败原因")
    private String errormsg;
}
