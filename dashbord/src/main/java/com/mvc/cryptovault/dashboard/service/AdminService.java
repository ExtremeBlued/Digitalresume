package com.mvc.cryptovault.dashboard.service;

import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.AdminUser;
import com.mvc.cryptovault.common.bean.dto.PageDTO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.bean.vo.TokenVO;
import com.mvc.cryptovault.common.constant.RedisConstant;
import com.mvc.cryptovault.common.dashboard.bean.dto.AdminDTO;
import com.mvc.cryptovault.common.dashboard.bean.dto.AdminPasswordDTO;
import com.mvc.cryptovault.common.dashboard.bean.dto.DUserDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.AdminDetailVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.AdminVO;
import com.mvc.cryptovault.common.util.BaseContextHandler;
import com.mvc.cryptovault.common.util.JwtHelper;
import com.mvc.cryptovault.common.util.MessageConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author qiyichen
 * @create 2018/11/19 19:58
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class AdminService extends BaseService {

    public PageInfo<AdminVO> getAdmins(PageDTO dto) {
        Result<PageInfo<AdminVO>> result = remoteService.getAdmins(getUserId(), dto);
        return result.getData();
    }

    public AdminDetailVO getAdminDetail(BigInteger id) {
        Result<AdminDetailVO> result = remoteService.getAdminDetail(id);
        return result.getData();
    }

    public Boolean deleteAdmin(BigInteger id) {
        Result<Boolean> result = remoteService.deleteAdmin(getUserId(), id);
        return result.getData();
    }

    public Boolean updatePwd(BigInteger id, AdminPasswordDTO adminPasswordDTO) {
        Result<Boolean> result = remoteService.updatePwd(getUserId(), adminPasswordDTO);
        return result.getData();
    }

    public TokenVO login(DUserDTO userDTO) {
