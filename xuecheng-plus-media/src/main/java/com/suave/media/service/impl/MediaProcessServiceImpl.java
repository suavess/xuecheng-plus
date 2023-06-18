package com.suave.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.media.entity.MediaFiles;
import com.suave.media.entity.MediaProcess;
import com.suave.media.entity.MediaProcessHistory;
import com.suave.media.mapper.MediaFilesMapper;
import com.suave.media.mapper.MediaProcessHistoryMapper;
import com.suave.media.mapper.MediaProcessMapper;
import com.suave.media.service.IMediaProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2023-06-09
 */
@Slf4j
@Service("mediaProcessService")
public class MediaProcessServiceImpl extends ServiceImpl<MediaProcessMapper, MediaProcess> implements IMediaProcessService {
    @Autowired
    private MediaProcessHistoryMapper mediaProcessHistoryMapper;
    @Autowired
    private MediaFilesMapper mediaFilesMapper;

    @Override
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count) {
        return baseMapper.selectListByShardIndex(shardTotal, shardIndex, count);
    }

    public boolean startTask(long id) {
        return baseMapper.startTask(id) > 0;
    }

    @Override
    public void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg) {

        // 要更新的任务
        MediaProcess mediaProcess = baseMapper.selectById(taskId);
        if (mediaProcess == null) {
            return;
        }
        // 如果任务执行失败
        if (status.equals("3")) {
            // 更新MediaProcess表的状态
            LambdaUpdateWrapper<MediaProcess> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(MediaProcess::getId, taskId);
            updateWrapper.set(MediaProcess::getStatus, "3");
            updateWrapper.set(MediaProcess::getErrormsg, errorMsg);
            updateWrapper.setSql("fail_count = fail_count + 1");
            update(updateWrapper);
            return;

        }


        //======如果任务执行成功======
        // 文件表记录
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileId);
        // 更新media_file表中的url
        mediaFiles.setUrl(url);
        mediaFilesMapper.updateById(mediaFiles);

        // 更新MediaProcess表的状态
        mediaProcess.setStatus("2");
        mediaProcess.setFinishDate(LocalDateTime.now());
        mediaProcess.setUrl(url);
        baseMapper.updateById(mediaProcess);

        // 将MediaProcess表记录插入到MediaProcessHistory表
        MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
        BeanUtils.copyProperties(mediaProcess, mediaProcessHistory);
        mediaProcessHistoryMapper.insert(mediaProcessHistory);

        // 从MediaProcess删除当前任务
        baseMapper.deleteById(taskId);
    }
}
