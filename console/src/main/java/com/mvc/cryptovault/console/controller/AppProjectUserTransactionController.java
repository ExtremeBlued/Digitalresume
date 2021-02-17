package com.mvc.cryptovault.console.controller;

import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.AppProject;
import com.mvc.cryptovault.common.bean.dto.ProjectBuyDTO;
import com.mvc.cryptovault.common.bean.dto.ReservationDTO;
import com.mvc.cryptovault.common.bean.vo.ProjectBuyVO;
import com.mvc.cryptovault.common.bean.vo.PurchaseVO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.console.common.BaseController;
import com.mvc.cryptovault.console.service.AppProjectService;
import com.mvc.cryptovault.console.service.AppProjectUserTransactionService;
import com.mvc.cryptovault.console.service.AppUserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @author qiyichen
 * @create 2018/11/13 17:23
 */
@RestController
@RequestMapping("appProjectUserTransaction")
public class AppProjectUserTransactionController extends BaseController {

    @Autowired
    AppProjectUserTransactionService appProjectUserTransactionService;
    @Autowired
    AppProjectService appProjectService;
    @Autowired
    AppUserBalanceService appUserBalanceService;

    @GetMapping()
    Result<PageInfo<PurchaseVO>> getReservation(@RequestParam("us