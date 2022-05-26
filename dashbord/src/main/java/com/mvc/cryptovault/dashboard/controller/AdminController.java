package com.mvc.cryptovault.dashboard.controller;

import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.dto.PageDTO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.bean.vo.TokenVO;
import com.mvc.cryptovault.common.dashboard.bean.dto.AdminDTO;
import com.mvc.cryptovault.common.dashboard.bean.dto.AdminPasswordDTO;
import com.mvc.cryptovault.common.dashboard.bean.dto.DUserDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.AdminDetailVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.AdminVO;
import com.mvc.cryptovault.common.permission.NotLogin;
import com.mvc.cryptovault.dashboard.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author qiyichen
 * @create 2018/11/19 19:48
 */
@Api(tags = "管理员相关")
@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {

    @Autowired
    private OssService ossService;
    @ApiOperation("获取所有管理员")
    @GetMapping()
    public Result<PageInfo<AdminVO>> getAdmins(@ModelAttribute @Valid PageDTO dto) {
        return new Result<>(adminService.getAdmins(dto));
    }

    @ApiOperation("获取管理员详情")
    @GetMapping("{id}")
    public Result<AdminDetailVO> getAdminDetail(@PathVariable BigInteger id) {
        retu