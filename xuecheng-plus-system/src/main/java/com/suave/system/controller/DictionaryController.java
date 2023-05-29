package com.suave.system.controller;

import com.suave.system.entity.Dictionary;
import com.suave.system.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author Suave
 * @since 2023-05-29
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    @Autowired
    private IDictionaryService dictionaryService;

    @GetMapping("/all")
    public List<Dictionary> queryAll() {
        return dictionaryService.query().list();
    }

    @GetMapping("/code/{code}")
    public Dictionary queryAll(@PathVariable String code) {
        return dictionaryService.lambdaQuery().eq(Dictionary::getCode, code).one();
    }
}
