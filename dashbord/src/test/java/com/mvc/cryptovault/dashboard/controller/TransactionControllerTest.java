package com.mvc.cryptovault.dashboard.controller;

import com.mvc.cryptovault.common.dashboard.bean.vo.DTransactionVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.OverTransactionVO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class TransactionControllerTest extends BaseTest {

    String controller = "/transaction";

    @Test
    public void findTransaction() throws Exception {
        String url = host + controller + "";
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .header("Authorization", getToken().getToken())
                .param("pairId", "")
                .param("transactionType", "")
                .param("orderNumber", "")
                .param("cellphone", "")
                .param("status", "")
                