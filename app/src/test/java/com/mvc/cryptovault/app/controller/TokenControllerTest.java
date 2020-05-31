package com.mvc.cryptovault.app.controller;

import com.alibaba.fastjson.TypeReference;
import com.mvc.cryptovault.app.base.BaseTest;
import com.mvc.cryptovault.common.bean.vo.TokenDetailVO;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class TokenControllerTest extends BaseTest {

    @Test
    public void getTokens() throws Exception {
        //查询所有
        MvcRes