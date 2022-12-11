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

    String contr