package com.mvc.cryptovault.console.dashboard.controller;

import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.AppUser;
import com.mvc.cryptovault.common.bean.dto.PageDTO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.bean.vo.TokenBalanceVO;
import com.mvc.cryptovault.common.constant.RedisConstant;
import com.mvc.cryptovault.common.dashboard.bean.dto.DUSerVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DUSerDetailVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DUserBalanceVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DUserLogVO;
import com.mvc.cryptovault.console.common.BaseController;
import com.mvc.cryptovault.console.service.AppUserBalanceService;
import com.mvc.cryptovault.console.service.AppUserService;
import com.mvc.cryptovault.console.service.BlockSignService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiyichen
 * @create 2018/11/21 16:43
 */
@RestController
@RequestMapping("dashboard/appUser")
public class DAppUserController extends BaseController {
    @Autowired
    AppUserService appUserService;
    @Autowired
    AppUserBalanceService appUserBalanceService;
    @Autowired
    BlockSignService blockSignService;

    @GetMapping("")
    public Result<PageInfo<DUSerVO>> findUser(@ModelAttribute PageDTO pageDTO, @RequestParam(value = "cellphone", required = false) String cellphone, @RequestParam(value = "status", required = false) Integer status) {
        PageInfo<DUSerVO> result = appUserService.findUser(pageDTO, cellphone, status);
        return new Result<>(result);
    }

    @GetMapping("{id}")
    public Result<DUSerDetailVO> getUserDetail(@PathVariable("id") BigInteger id) {
        AppUser user = appUserService.findById(id);
        DUSerDetailVO result = new DUSerDetailVO();
        BeanUtils.copyProperties(user, result);
        ret