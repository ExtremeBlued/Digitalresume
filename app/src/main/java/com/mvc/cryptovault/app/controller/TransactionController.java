package com.mvc.cryptovault.app.controller;

import com.mvc.cryptovault.common.bean.dto.MyTransactionDTO;
import com.mvc.cryptovault.common.bean.dto.OrderDTO;
import com.mvc.cryptovault.common.bean.dto.PairDTO;
import com.mvc.cryptovault.common.bean.dto.TransactionBuyDTO;
import com.mvc.cryptovault.common.bean.vo.*;
import com.mvc.cryptovault.common.swaggermock.SwaggerMock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

/**
 * 本项目不涉及撮合交易
 *
 * @author qiyichen
 * @create 2018/11/7 17:26
 */
@RestController
@Api(tags = "交易相关")
@RequestMapping("transaction")
public class TransactionController extends BaseController {

    @ApiOperation("获取交易对,很少变动,本地必须缓存")
    @GetMappi