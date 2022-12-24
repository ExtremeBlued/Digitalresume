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
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        action.andExpect(MockMvcResultMatchers.jsonPath("data").isNotEmpty());
        addNullActionTest(action, "data[0]", DTokenVO.class);
        MvcResult result = action.andReturn();
    }

    @Test
    public void newToken() throws Exception {
        DTokenDTO dTokenDTO = new DTokenDTO();
        dTokenDTO.setBlockType("ETH");
        dTokenDTO.setContractAddress("");
        dTokenDTO.setDecimals(10);
        dTokenDTO.setTokenCnName("小牛");
        dTokenDTO.setTokenEnName(("MVC" + Math.random()).substring(0, 15));
        dTokenDTO.setTokenName(dTokenDTO.getTokenEnName());
        dTokenDTO.setTokenImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&");
        String url = host + controller + "";
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .header("Authorization", getToken().getToken())
                .content(JSON.toJSONString(dTokenDTO))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        action.andExpect(MockMvcResultMatchers.jsonPath("data").isNotEmpty());
        MvcResult result = action.andReturn();
    }

    @Test
    public void updateToken() throws Exception {
        DTokenDTO dTokenDTO = new DTokenDTO();
        dTokenDTO.setTokenId(BigInteger.valueOf(5));
        dTokenDTO.setBlockType("ETH");
        dTokenDTO.setContractAddress("");
        dTokenDTO.setDecimals(10);
        dTokenDTO.setTokenCnName("小牛");
        dTokenDTO.setTokenEnName("MVC");
        dTokenDTO.setTokenName("MVC");
        dTokenDTO.setTokenImage("https://timgsa.baidu.com/timg?image&qu