package com.mvc.cryptovault.console.dashboard.controller;

import com.mvc.cryptovault.common.bean.CommonAddress;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.constant.RedisConstant;
import com.mvc.cryptovault.console.common.BaseController;
import com.mvc.cryptovault.console.service.BlockHeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
i