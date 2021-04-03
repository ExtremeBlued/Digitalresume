package com.mvc.cryptovault.console.dashboard.controller;

import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.AdminUser;
import com.mvc.cryptovault.common.bean.AdminWallet;
import com.mvc.cryptovault.common.bean.CommonToken;
import com.mvc.cryptovault.common.bean.dto.PageDTO;
import com.mvc.cryptovault.common.bean.vo.AdminWalletVO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.dashboard.bean.dto.AdminDTO;
import com.mvc.cryptovault.common.dashboard.bean.dto.AdminPasswordDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.AdminDetailVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.AdminVO;
import com.mvc.cryptovault.console.common.BaseController;
import com.mvc.cryptovault.console.service.*;
import com.mvc.cryptovault.console.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author qiyichen
 * @create 2018/11/21 16:37
 */
@RestController
@RequestMapping("dashboard/adminUser")
public class DAdminUserController extends BaseController {

    @Autowired
    AdminUserService adminUserService;
    @Autowired
    CommonTokenService commonTokenService;
    @Autowired
    CommonAddressService commonAddressService;
    @Autowired
    AdminWalletService adminWalletService;
    @Autowired
    BlockHeightService blockHeightService;

    @GetMapping()
    public Result<PageInfo<AdminVO>> getAdmins(@RequestParam BigInteger userId, @ModelAttribute PageDTO dto) {
        AdminUser user = adminUserService.findById(userId);
        //非超级管理员只能看到自己
        if (null != user && user.getAdminType() == 1) {
            AdminVO vo = new AdminVO();
            BeanUtils.copyProperties(user, vo);
            return new Result<>(new PageInfo<>(Arrays.asList(vo)));
        }
        List<AdminUser> list = adminUserService.findAll();
        Integer total = list.size();
        list = PageUtil.subList(list, dto);
        List<AdminVO> vos = new ArrayList<>(list.size());
        list.forEach(obj -> {
            AdminVO vo = new AdminVO();
            BeanUtils.copyProperties(obj, vo);
            vos.add(vo);
        });
        PageInfo result = new PageInfo<>(list);
        result.setList(vos);
        result.setTotal(total);
        return new Result<>(result);
    }

    @GetMapping("{id}")
    public Result<AdminDetailVO> getAdminDetail(@PathVariable BigInteger id) {
        AdminDetailVO result = adminUserService.getAdminDetail(id);
        return new Result<>(result);
    }

    @