package com.mvc.cryptovault.dashboard.service;

import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.dto.PageDTO;
import com.mvc.cryptovault.common.bean.vo.DPairVO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.dashboard.bean.dto.DTokenDTO;
import com.mvc.cryptovault.common.dashboard.bean.dto.OverTransactionDTO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DTokenSettingVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DTokenTransSettingVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DTokenVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.OverTransactionVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Tr