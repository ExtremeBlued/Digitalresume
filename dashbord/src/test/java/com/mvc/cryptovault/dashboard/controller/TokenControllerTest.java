package com.mvc.cryptovault.dashboard.controller;

import com.alibaba.fastjson.JSON;
import com.mvc.cryptovault.common.dashboard.bean.dto.DTokenDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DTokenSettingVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DTokenTransSettingVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DTokenVO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class TokenControllerTest extends BaseTest {
    String controller = "/token";

    @Test
    public void findTokens() throws Exception {
        String url = host + controller + "";
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .header("Authorization", getToken().getToken())
                .param("tokenName", "")
                .contentType(MediaType.APPLICATION_JS