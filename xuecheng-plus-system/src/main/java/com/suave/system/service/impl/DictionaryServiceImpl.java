package com.suave.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suave.system.entity.Dictionary;
import com.suave.system.mapper.DictionaryMapper;
import com.suave.system.service.IDictionaryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Suave
 * @since 2023-05-29
 */
@Service("dictionaryService")
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

}
