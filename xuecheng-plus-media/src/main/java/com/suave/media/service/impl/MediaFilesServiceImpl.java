package com.suave.media.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.base.dto.PageDTO;
import com.suave.base.exception.XCPException;
import com.suave.base.vo.PageVO;
import com.suave.media.dto.QueryMediaParamsDto;
import com.suave.media.dto.UploadFileDTO;
import com.suave.media.entity.MediaFiles;
import com.suave.media.mapper.MediaFilesMapper;
import com.suave.media.service.IMediaFilesService;
import com.suave.media.vo.UploadFileVO;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 媒资信息 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2023-06-09
 */
@Slf4j
@Service("mediaFilesService")
public class MediaFilesServiceImpl extends ServiceImpl<MediaFilesMapper, MediaFiles> implements IMediaFilesService {

    @Autowired
    private MediaFilesMapper mediaFilesMapper;

    @Autowired
    private MinioClient minioClient;

    /**
     * 存储普通文件
     */
    @Value("${minio.bucket.files}")
    private String bucketMediaFiles;

    /**
     * 存储视频
     */
    @Value("${minio.bucket.videofiles}")
    private String bucketVideo;

    @Override
    public PageVO<MediaFiles> queryMediaFiels(Long companyId, PageDTO pageParams, QueryMediaParamsDto queryMediaParamsDto) {
        // 构建查询条件对象
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();
        // 分页对象
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<MediaFiles> pageResult = mediaFilesMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<MediaFiles> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        return new PageVO<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Override
    public UploadFileVO uploadFile(Long companyId, UploadFileDTO uploadFileDTO, String localFilePath) {
        // 文件名
        String filename = uploadFileDTO.getFilename();
        // 先得到扩展名
        String extension = filename.substring(filename.lastIndexOf("."));

        // 得到mimeType
        String mimeType = FileUtil.getMimeType(extension);

        // 子目录
        String defaultFolderPath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        // 文件的md5值
        String fileMd5 = SecureUtil.md5(new File(localFilePath));
        String objectName = defaultFolderPath + fileMd5 + extension;
        // 上传文件到minio
        boolean result = addMediaFilesToMinIO(localFilePath, mimeType, bucketMediaFiles, objectName);
        if (!result) {
            throw new XCPException("上传文件失败");
        }
        // 入库文件信息
        MediaFiles mediaFiles = addMediaFilesToDb(companyId, fileMd5, uploadFileDTO, bucketMediaFiles, objectName);
        if (mediaFiles == null) {
            throw new XCPException("文件上传后保存信息失败");
        }
        // 准备返回的对象
        return BeanUtil.copyProperties(mediaFiles, UploadFileVO.class);
    }

    /**
     * 将文件上传到minio
     *
     * @param localFilePath 文件本地路径
     * @param mimeType      媒体类型
     * @param bucket        桶
     * @param objectName    对象名
     * @return
     */
    public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    // 桶
                    .bucket(bucket)
                    // 指定本地文件路径
                    .filename(localFilePath)
                    // 对象名 放在子目录下
                    .object(objectName)
                    // 设置媒体文件类型
                    .contentType(mimeType)
                    .build();
            // 上传文件
            minioClient.uploadObject(uploadObjectArgs);
            log.debug("上传文件到minio成功,bucket:{},objectName:{}", bucket, objectName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件出错,bucket:{},objectName:{},错误信息:", bucket, objectName, e);
        }
        return false;
    }


    /**
     * @param companyId     机构id
     * @param fileMd5       文件md5值
     * @param uploadFileDTO 上传文件的信息
     * @param bucket        桶
     * @param objectName    对象名称
     * @return com.xuecheng.media.model.po.MediaFiles
     * @description 将文件信息添加到文件表
     * @author Mr.M
     * @date 2022/10/12 21:22
     */
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileDTO uploadFileDTO, String bucket, String objectName) {
        // 将文件信息保存到数据库
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles == null) {
            mediaFiles = new MediaFiles();
            BeanUtils.copyProperties(uploadFileDTO, mediaFiles);
            // 文件id
            mediaFiles.setId(fileMd5);
            // 机构id
            mediaFiles.setCompanyId(companyId);
            // 桶
            mediaFiles.setBucket(bucket);
            // file_path
            mediaFiles.setFilePath(objectName);
            // file_id
            mediaFiles.setFileId(fileMd5);
            // url
            mediaFiles.setUrl("/" + bucket + "/" + objectName);
            // 上传时间
            mediaFiles.setCreateDate(LocalDateTime.now());
            // 状态
            mediaFiles.setStatus("1");
            // 审核状态
            mediaFiles.setAuditStatus("002003");
            // 插入数据库
            int insert = mediaFilesMapper.insert(mediaFiles);
            if (insert <= 0) {
                log.debug("向数据库保存文件失败,bucket:{},objectName:{}", bucket, objectName);
                return null;
            }
            return mediaFiles;

        }
        return mediaFiles;

    }
}
