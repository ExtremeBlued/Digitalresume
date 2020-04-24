package com.mvc.cryptovault.app.service;

import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.app.feign.ConsoleRemoteService;
import com.mvc.cryptovault.common.bean.AppProject;
import com.mvc.cryptovault.common.bean.dto.ProjectBuyDTO;
import com.mvc.cryptovault.common.bean.dto.ProjectDTO;
import com.mvc.cryptovault.common.bean.dto.ReservationDTO;
import com.mvc.cryptovault.common.bean.vo.ProjectBuyVO;
import com.mvc.cryptovault.common.bean.vo.ProjectSimpleVO;
import com.mvc.cryptovault.common.bean.vo.PurchaseVO;
import com.mvc.cryptovault.common.bean.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Ser