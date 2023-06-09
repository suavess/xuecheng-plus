package com.suave.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.media.entity.MqMessage;
import com.suave.media.mapper.MqMessageMapper;
import com.suave.media.service.IMqMessageService;
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
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage> implements IMqMessageService {

}
