package com.mvc.cryptovault.dashboard.controller;

import com.alibaba.fastjson.JSON;
import com.mvc.cryptovault.common.dashboard.bean.dto.DProjectDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DProjectDetailVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DProjectOrderVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DProjectVO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ProjectControllerTest extends BaseTest {

    String controller = "/project";

    @Test
    public void projects() throws Exception {
        String url = host + controller + "";
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .header("Authorization", getToken().getToken())
                .param("pageSize", "1")
                .param("pageNum", "1")
                .param("updatedStartAt", "")
                .param("orderBy", "id desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status(