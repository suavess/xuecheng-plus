package com.suave.media.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.nacos.common.http.param.MediaType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.base.dto.PageDTO;
import com.suave.base.exception.XCPException;
import com.suave.base.vo.PageVO;
import com.suave.base.vo.RestResponse;
import com.suave.media.dto.QueryMediaParamsDto;
import com.suave.media.dto.UploadFileDTO;
import com.suave.media.entity.MediaFiles;
import com.suave.media.mapper.MediaFilesMapper;
import com.suave.media.service.IMediaFilesService;
import com.suave.media.vo.UploadFileVO;
import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.UploadObjectArgs;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Page<MediaFiles> pageResult = baseMapper.selectPage(page, queryWrapper);
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
        if (!addMediaFilesToMinIO(localFilePath, mimeType, bucketMediaFiles, objectName)) {
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
    @Override
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
        MediaFiles mediaFiles = baseMapper.selectById(fileMd5);
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
            int insert = baseMapper.insert(mediaFiles);
            if (insert <= 0) {
                log.debug("向数据库保存文件失败,bucket:{},objectName:{}", bucket, objectName);
                return null;
            }
            return mediaFiles;

        }
        return mediaFiles;

    }

    @Override
    public RestResponse<Boolean> checkFile(String fileMd5) {
        // 先查询数据库
        MediaFiles mediaFiles = baseMapper.selectById(fileMd5);
        if (mediaFiles != null) {
            // 桶
            String bucket = mediaFiles.getBucket();
            // object name
            String filePath = mediaFiles.getFilePath();
            // 如果数据库存在再查询 minio
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .build();
            // 查询远程服务获取到一个流对象
            try {
                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
                if (inputStream != null) {
                    // 文件已存在
                    return RestResponse.success(Boolean.TRUE);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        // 文件不存在
        return RestResponse.success(Boolean.FALSE);
    }

    @Override
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex) {
        // 根据md5得到分块文件所在目录的路径
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);

        // 如果数据库存在再查询 minio
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketVideo)
                .object(chunkFileFolderPath + chunkIndex)
                .build();
        // 查询远程服务获取到一个流对象
        try {
            FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
            if (inputStream != null) {
                // 文件已存在
                return RestResponse.success(true);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        // 文件不存在
        return RestResponse.success(false);
    }

    @Override
    public RestResponse<Boolean> uploadChunk(String fileMd5, int chunk, String localFilePath) {
        // 分块文件的路径
        String chunkFilePath = getChunkFileFolderPath(fileMd5) + chunk;
        String mimeType = MediaType.APPLICATION_OCTET_STREAM;
        // 将分块文件上传到minio
        if (!addMediaFilesToMinIO(localFilePath, mimeType, bucketVideo, chunkFilePath)) {
            return RestResponse.validFail(false, "上传分块文件失败");
        }
        // 上传成功
        return RestResponse.success(true);
    }

    @Override
    public RestResponse<Boolean> mergechunks(long companyId, String fileMd5, int chunkTotal, UploadFileDTO uploadFileDTO) {
        // 分块文件所在目录
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        // 找到所有的分块文件
        List<ComposeSource> sources = Stream.iterate(0, i -> ++i).limit(chunkTotal).map(i -> ComposeSource.builder().bucket(bucketVideo).object(chunkFileFolderPath + i).build()).collect(Collectors.toList());
        // 源文件名称
        String filename = uploadFileDTO.getFilename();
        // 扩展名
        String extension = filename.substring(filename.lastIndexOf("."));
        // 合并后文件的objectname
        String objectName = getFilePathByMd5(fileMd5, extension);
        // 指定合并后的objectName等信息
        ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder()
                .bucket(bucketVideo)
                // 合并后的文件的object name
                .object(objectName)
                // 指定源文件
                .sources(sources)
                .build();
        //===========合并文件============
        // 报错size 1048576 must be greater than 5242880，minio默认的分块文件大小为5M
        try {
            minioClient.composeObject(composeObjectArgs);
        } catch (Exception e) {
            log.error("合并文件出错,bucket:{},objectName:{},错误信息:", bucketVideo, objectName, e);
            return RestResponse.validFail(false, "合并文件异常");
        }

        //===========校验合并后的和源文件是否一致，视频上传才成功===========
        // 先下载合并后的文件
        File file = downloadFileFromMinIO(bucketVideo, objectName);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            // 计算合并后文件的md5
            String mergeFileMd5 = DigestUtils.md5Hex(fileInputStream);
            // 比较原始md5和合并后文件的md5
            if (!fileMd5.equals(mergeFileMd5)) {
                log.error("校验合并文件md5值不一致,原始文件:{},合并文件:{}", fileMd5, mergeFileMd5);
                return RestResponse.validFail(false, "文件校验失败");
            }
            // 文件大小
            uploadFileDTO.setFileSize(file.length());
        } catch (Exception e) {
            return RestResponse.validFail(false, "文件校验失败");
        }

        //==============将文件信息入库============
        MediaFiles mediaFiles = addMediaFilesToDb(companyId, fileMd5, uploadFileDTO, bucketVideo, objectName);
        if (mediaFiles == null) {
            return RestResponse.validFail(false, "文件入库失败");
        }
        //==========清理分块文件=========
        clearChunkFiles(chunkFileFolderPath, chunkTotal);

        return RestResponse.success(true);
    }

    /**
     * 得到合并后的文件的地址
     *
     * @param fileMd5 文件id即md5值
     * @param fileExt 文件扩展名
     * @return
     */
    private String getFilePathByMd5(String fileMd5, String fileExt) {
        return fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/" + fileMd5 + fileExt;
    }

    /**
     * 得到分块文件的目录
     *
     * @param fileMd5
     * @return
     */
    private String getChunkFileFolderPath(String fileMd5) {
        return fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/chunk/";
    }

    /**
     * 从minio下载文件
     *
     * @param bucket     桶
     * @param objectName 对象名称
     * @return 下载后的文件
     */
    @Override
    public File downloadFileFromMinIO(String bucket, String objectName) {
        // 临时文件
        File minioFile = null;
        FileOutputStream outputStream = null;
        try {
            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            // 创建临时文件
            minioFile = File.createTempFile("minio", ".merge");
            outputStream = new FileOutputStream(minioFile);
            IoUtil.copy(stream, outputStream);
            return minioFile;
        } catch (Exception e) {
            log.error("", e);
        } finally {
            IoUtil.close(outputStream);
        }
        return null;
    }

    /**
     * 清除分块文件
     *
     * @param chunkFileFolderPath 分块文件路径
     * @param chunkTotal          分块文件总数
     */
    private void clearChunkFiles(String chunkFileFolderPath, int chunkTotal) {
        Iterable<DeleteObject> objects = Stream.iterate(0, i -> ++i).limit(chunkTotal).map(i -> new DeleteObject(chunkFileFolderPath + i)).collect(Collectors.toList());
        ;
        RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder().bucket(bucketVideo).objects(objects).build();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
        // 要想真正删除
        results.forEach(f -> {
            try {
                f.get();
            } catch (Exception e) {
                log.error("", e);
            }
        });
    }


}
