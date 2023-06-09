package com.suave.media.controller;

import com.suave.base.dto.PageDTO;
import com.suave.base.vo.PageVO;
import com.suave.media.dto.QueryMediaParamsDto;
import com.suave.media.entity.MediaFiles;
import com.suave.media.service.IMediaFilesService;
import com.suave.media.vo.UploadFileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 媒资信息 前端控制器
 * </p>
 *
 * @author Suave
 * @since 2023-06-09
 */
@Tag(name = "媒资文件管理接口", description = "媒资文件管理接口")
@RestController
@RequestMapping
public class MediaFilesController {

    @Autowired
    private IMediaFilesService mediaFileService;


    @Operation(summary = "媒资列表查询接口")
    @PostMapping("/files")
    public PageVO<MediaFiles> list(PageDTO pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto) {
        Long companyId = 1232141425L;
        return mediaFileService.queryMediaFiels(companyId, pageParams, queryMediaParamsDto);
    }

    @Operation(summary = "上传图片")
    @RequestMapping(value = "/upload/coursefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileVO upload(@RequestPart("filedata") MultipartFile fileData) {
        return null;
    }
}
