package com.mvc.cryptovault.console.controller;

import com.mvc.cryptovault.common.bean.AppUser;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.console.common.BaseController;
import com.mvc.cryptovault.console.service.AppUserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static com.mvc.cryptovault.common.constant.RedisConstant.APP_USER_USERNAME;

/**
 * @author qiyichen
 * @create 2018/11/12 14:35
 */
@RequestMapping("user")
@RestController
public class UserController extends BaseController {

    @Autowired
    AppUserService appUserService;

    @GetMapping("username")
    public Result<AppUser> getByUsername(@RequestParam String username) {
        String key = APP_USER_USERNAME + user