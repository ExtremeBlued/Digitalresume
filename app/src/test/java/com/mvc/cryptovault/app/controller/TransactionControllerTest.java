package com.mvc.cryptovault.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mvc.cryptovault.app.base.BaseTest;
import com.mvc.cryptovault.common.bean.dto.TransactionBuyDTO;
import com.mvc.cryptovault.common.bean.vo.MyOrderVO;
import com.mvc.cryptovault.common.bean.vo.OrderInfoVO;
import com.mvc.cryptovault.common.bean.vo.OrderVO;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class TransactionControllerTest extends BaseTest {

    @Test
    public void getPair() throws Exception {
        MvcResult result = null;
        result = mockMvc.perform(MockMvcRequestBuilders.get(host + "/transaction/pair")
                .header("Authorization", getToken().getToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("data").isNotEmpty())
                .andReturn();
    }

    @Test
    public void getTransactions() throws Exception {
        MvcResult result = null;
        result = mockMvc.perform(MockMvcRequestBuilders.get(host + "/transaction")
                .header("Authorization", getToken().getToken())
                .param("pairId", "1")
                .param("transactionType", "1")
                .param("id", "0")
                .param("type", "0")
                .param("pageSize", "999")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("data[0].headImage").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("data[0].limitValue").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("data[0].nickname").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("data[0].total").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("data[0].id").isNotEmpty())
                .andReturn();
        var data = parseObject(result, new 