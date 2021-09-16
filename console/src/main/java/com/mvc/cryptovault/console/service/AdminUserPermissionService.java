package com.mvc.cryptovault.console.service;

import com.mvc.cryptovault.common.bean.AdminUserPermission;
import com.mvc.cryptovault.common.dashboard.bean.dto.PermissionDTO;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.dao.AdminUserPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserPermissionService extends AbstractService<AdminUserPermission> implements BaseService<AdminUserPermission> {

    @Autowired
    AdminUserPermissionMapper adminUserPermissionMapper;

    public void delete(BigInteger id) {
        AdminUserPermission permission = new AdminUserPermission();
        permission.setUserId(id);
        adminUserPermissionMapper.delete