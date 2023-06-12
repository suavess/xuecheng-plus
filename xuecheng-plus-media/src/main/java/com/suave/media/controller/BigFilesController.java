package com.suave.media.controller;

import com.suave.base.vo.RestResponse;
import com.suave.media.dto.UploadFileDTO;
import com.suave.media.service.IMediaFilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Mr.M
 * @version 1.0
 * @description 上传视频
 * @date 2023/2/18 10:34
 */

@Tag(name = "大文件上传接口", description = "大文件上传接口")
@RestController
public class BigFilesController {

    @Autowired
    private IMediaFilesService mediaFilesService;


    @Operation(summary = "文件上传前检查文件")
    @PostMapping("/upload/checkfile")
    public RestResponse<Boolean> checkfile(@RequestParam("fileMd5") String fileMd5) throws Exception {
        return mediaFilesService.checkFile(fileMd5);
    }


    @Operation(summary = "分块文件上传前的检测")
    @PostMapping("/upload/checkchunk")
    public RestResponse<Boolean> checkchunk(@RequestParam("fileMd5") String fileMd5, @RequestParam("chunk") int chunk) throws Exception {
        return mediaFilesService.checkChunk(fileMd5, chunk);
    }

    @Operation(summary = "上传分块文件")
    @PostMapping("/upload/uploadchunk")
    public RestResponse<Boolean> uploadchunk(@RequestParam("file") MultipartFile file,
                                             @RequestParam("fileMd5") String fileMd5,
                                             @RequestParam("chunk") int chunk) throws Exception {

        // 创建一个临时文件
        File tempFile = File.createTempFile("minio", ".temp");
        file.transferTo(tempFile);
        // 文件路径
        String localFilePath = tempFile.getAbsolutePath();

        return mediaFilesService.uploadChunk(fileMd5, chunk, localFilePath);
    }

    @Operation(summary = "合并文件")
    @PostMapping("/upload/mergechunks")
    public RestResponse<Boolean> mergechunks(@RequestParam("fileMd5") String fileMd5,
                                             @RequestParam("fileName") String fileName,
                                             @RequestParam("chunkTotal") int chunkTotal) throws Exception {
        Long companyId = 1232141425L;
        // 文件信息对象
        UploadFileDTO uploadFileDTO = new UploadFileDTO();
        uploadFileDTO.setFilename(fileName);
        uploadFileDTO.setTags("视频文件");
        uploadFileDTO.setFileType("001002");
        return mediaFilesService.mergechunks(1232141425L, fileMd5, chunkTotal, uploadFileDTO);

    }


}
