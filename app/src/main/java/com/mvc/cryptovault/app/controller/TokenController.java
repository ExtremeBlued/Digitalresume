package com.mvc.cryptovault.app.controller;

import com.mvc.cryptovault.app.service.TokenService;
import com.mvc.cryptovault.common.bean.vo.ExchangeRateVO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.bean.vo.TokenDetailVO;
import com.mvc.cryptovault.common.bean.vo.TokenRatioVO;
import com.mvc.cryptovault.common.swaggermock.SwaggerMock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

/**
 * 令牌相关
 *
 * @author qiyichen
 * @create 2018/11