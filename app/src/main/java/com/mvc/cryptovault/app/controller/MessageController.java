package com.mvc.cryptovault.app.controller;

import com.mvc.cryptovault.app.bean.dto.PageDTO;
import com.mvc.cryptovault.common.bean.dto.TimeSearchDTO;
import com.mvc.cryptovault.common.bean.vo.MessageVO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.swaggermock.SwaggerMock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * 消息相