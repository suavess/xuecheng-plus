package com.suave.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suave.system.entity.Dictionary;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author Suave
 * @since 2023-05-29
 */
@Mapper
public interface DictionaryMapper extends BaseMapper<Dictionary> {

}
