package com.mvc.cryptovault.console.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.mvc.cryptovault.common.bean.AdminPermission;
import com.mvc.cryptovault.common.bean.AdminUser;
import com.mvc.cryptovault.common.bean.AdminUserPermission;
import com.mvc.cryptovault.common.dashboard.bean.dto.AdminDTO;
import com.mvc.cryptovault.common.dashboard.bean.dto.PermissionDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.AdminDetailVO;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AdminUserService extends Abstr