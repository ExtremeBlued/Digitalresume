package com.mvc.cryptovault.console.controller;

import com.mvc.cryptovault.common.bean.AppUser;
import com.mvc.cryptovault.common.bean.dto.AssertVisibleDTO;
import com.mvc.cryptovault.common.bean.dto.DebitDTO;
import com.mvc.cryptovault.common.bean.dto.DebitRechargeDTO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.bean.vo.TokenBalanceVO;
import com.mvc.cryptovault.common.util.MessageConstants;
import com.mvc.cryptovault.console.common.BaseController;
import com.mvc.cryptovault.console.constant.BusinessConstant;
import com.mvc.cryptovault.console.service.AppUserBalanceService;
import com.mvc.cryptovault.console.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author qiyichen
 * @create 2018/11/14 14:38
 */
@RestController
@RequestMapping("appUserBalance")
public class AppUserBalanceController extends BaseController {

    @Autowired
    AppUserBalanceService appUserBalanceService;
    @Autowired
    AppUserService appUserService;

    @GetMapping("{userId}")
    public Result<List<TokenBalanceVO>> getAsset(@PathVariable("userId") BigInteger userId) {
        List<TokenBalanceVO> list = appUserBalanceService.getAsset(userId, false);
        return new Result<>(list);
    }

    @PutMapping("{userId}")
    public Result<Boolean> setAssetVisible(@RequestBody @Valid AssertVisibleDTO visibleDTO, @PathVariable("userId") BigInteger userId) {
        appUserBalanceService.setAssetVisible(visibleDTO, userId);
        return new Result<>(true);
    }

    @GetMapping("sum/{userId}")
    public Resu