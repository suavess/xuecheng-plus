package com.suave.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suave.base.dto.PageDTO;
import com.suave.base.vo.PageVO;
import com.suave.base.vo.RestResponse;
import com.suave.media.dto.QueryMediaParamsDto;
import com.suave.media.dto.UploadFileDTO;
import com.suave.media.entity.MediaFiles;
import com.suave.media.vo.UploadFileVO;

/**
 * <p>
 * 媒资信息 服务类
 * </p>
 *
 * @author Suave
 * @since 2023-06-09
 */
public interface IMediaFilesService extends IService<MediaFiles> {

    PageVO<MediaFiles> queryMediaFiels(Long companyId, PageDTO pageParams, QueryMediaParamsDto queryMediaParamsDto);

    UploadFileVO uploadFile(Long companyId, UploadFileDTO uploadFileDTO, String localFilePath);

    MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileDTO uploadFileDTO, String bucket, String objectName);

    RestResponse<Boolean> checkFile(String fileMd5);

    RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    RestResponse<Boolean> uploadChunk(String fileMd5, int chunk, String localFilePath);

    RestResponse<Boolean> mergechunks(long companyId, String fileMd5, int chunkTotal, UploadFileDTO uploadFileDTO);
}
