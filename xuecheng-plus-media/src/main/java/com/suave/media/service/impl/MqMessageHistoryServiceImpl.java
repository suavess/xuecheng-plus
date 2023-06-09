package com.suave.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.media.entity.MqMessageHistory;
import com.suave.media.mapper.MqMessageHistoryMapper;
import com.suave.media.service.IMqMessageHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2023-06-09
 */
@Service
public class MqMessageHistoryServiceImpl extends ServiceImpl<MqMessageHistoryMapper, MqMessageHistory> implements IMqMessageHistoryService {

}
