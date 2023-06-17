package com.suave.media.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Suave
 * @since 2023/06/17 22:00
 */
@Slf4j
@Component
public class SampleXxlJob {


    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        log.info("处理视频.......");


    }


    @XxlJob("demoJobHandler2")
    public void demoJobHandler2() throws Exception {
        log.info("处理文档.......");

        // default success
    }

    /**
     * 2、分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() throws Exception {

        // 分片参数
        // 执行器的序号，从0开始
        int shardIndex = XxlJobHelper.getShardIndex();
        // 执行器总数
        int shardTotal = XxlJobHelper.getShardTotal();

        log.info("shardIndex=" + shardIndex + ",shardTotal=" + shardTotal);

    }
}
