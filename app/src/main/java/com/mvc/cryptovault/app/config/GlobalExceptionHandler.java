package com.mvc.cryptovault.app.config;

import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.util.TokenErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStat