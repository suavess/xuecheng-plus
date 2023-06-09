package com.suave.media.service;

import com.suave.base.dto.PageDTO;
import com.suave.base.vo.PageVO;
import com.suave.media.dto.QueryMediaParamsDto;
import com.suave.media.entity.MediaFiles;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
