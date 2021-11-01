package com.mvc.cryptovault.console.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.AppProject;
import com.mvc.cryptovault.common.bean.AppProjectUserTransaction;
import com.mvc.cryptovault.common.bean.AppUser;
import com.mvc.cryptovault.common.bean.dto.ImportPartake;
import com.mvc.cryptovault.common.bean.dto.ProjectBuyDTO;
import com.mvc.cryptovault.common.bean.dto.ReservationDTO;
import com.mvc.cryptovault.common.bean.vo.ProjectBuyVO;
import com.mvc.cryptovault.common.bean.vo.PurchaseVO;
import com.mvc.cryptovault.common.dashboard.bean.dto.DProjectOrderDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DProjectOrderVO;
import com.mvc.cryptovault.common.util.ConditionUtil;
import com.mvc.cryptovault.common.util.MessageConstants;
import com.mvc.cryptovault.console.com