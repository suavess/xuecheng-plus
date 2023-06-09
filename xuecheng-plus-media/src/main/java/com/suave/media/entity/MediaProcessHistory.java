package com.suave.media.entity;

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
@TableName("media_process_history")
@Schema(name = "MediaProcessHistory对象", description = "")
public class MediaProcessHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Schema(name = "文件标识")
    private String fileId;

    @Schema(name = "文件名称")
    private String filename;

    @Schema(name = "存储源")
    private String bucket;

    @Schema(name = "状态,1:未处理，2：处理成功  3处理失败")
    private String status;

    @Schema(name = "上传时间")
    private LocalDateTime createDate;

    @Schema(name = "完成时间")
    private LocalDateTime finishDate;

    @Schema(name = "媒资文件访问地址")
    private String url;

    @Schema(name = "文件路径")
    private String filePath;

    @Schema(name = "失败原因")
    private String errormsg;
}
