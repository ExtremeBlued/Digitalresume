package com.mvc.cryptovault.console.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.AppProject;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.console.common.BaseController;
import com.mvc.cryptovault.console.constant.BusinessConstant;
import com.mvc.cryptovault.console.service.AppProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import 