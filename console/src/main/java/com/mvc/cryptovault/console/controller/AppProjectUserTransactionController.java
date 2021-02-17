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
import org.springframework.beans.factory.annotation.