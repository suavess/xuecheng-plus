package com.suave.content.mapper;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Suave
 * @since 2023/05/28 21:33
 */
@SpringBootTest
public class CourseBaseMapperTest {
    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Test
    void test(){
        System.out.println(JSON.toJSONString(courseBaseMapper.selectById(18)));
    }
}
