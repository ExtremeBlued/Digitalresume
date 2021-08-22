package com.mvc.cryptovault.console.job;

import com.mvc.cryptovault.common.bean.TokenVolume;
import com.mvc.cryptovault.common.constant.RedisConstant;
import com.mvc.cryptovault.console.service.CommonTokenControlNextService;
import com.mvc.cryptovault.console.service.CommonTokenControlService;
import com.mvc.cryptovault.console.service.CommonTokenPriceService;
import com.mvc.cryptovault.console.service.TokenVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.