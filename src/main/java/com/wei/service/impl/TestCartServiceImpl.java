package com.wei.service.impl;

import com.wei.mapper.TestCartMapper;
import com.wei.pojo.TestCart;
import com.wei.service.TestCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MyBatis Generator工具自动生成
 */
@Service
public class TestCartServiceImpl extends BaseServiceImpl<TestCart, Integer> implements TestCartService {
    @Autowired
    private TestCartMapper testCartMapper;

    public TestCartServiceImpl(TestCartMapper mapper) {
        this.testCartMapper= mapper;
        init(testCartMapper);
    }
}