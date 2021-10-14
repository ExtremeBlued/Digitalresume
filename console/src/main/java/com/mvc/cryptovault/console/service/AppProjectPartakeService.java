package com.mvc.cryptovault.console.service;

import com.mvc.cryptovault.common.bean.AppOrder;
import com.mvc.cryptovault.common.bean.AppProject;
import com.mvc.cryptovault.common.bean.AppProjectPartake;
import com.mvc.cryptovault.common.bean.dto.ImportPartake;
import com.mvc.cryptovault.common.util.ConditionUtil;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.dao.AppProjectPartakeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.