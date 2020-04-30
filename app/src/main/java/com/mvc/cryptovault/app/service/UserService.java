package com.mvc.cryptovault.app.service;

import com.mvc.cryptovault.app.feign.ConsoleRemoteService;
import com.mvc.cryptovault.common.bean.AppUser;
import com.mvc.cryptovault.common.bean.dto.UserDTO;
import com.mvc.cryptovault.common.bean.vo.Result;
import com.mvc.cryptovault.common.bean.vo.TokenVO;
import com.mvc.cryptovault.common.bean.vo.UserSimpleVO;
import com.mvc.cryptovault.common.util.BaseContextHandler;
import com.mvc.cryptovault.common.util.JwtHelper;
import com.mvc.cryptovault.common.util.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.security.auth.login.LoginException;
import java.math.BigInteger;

@Service
public class UserService {

    @Autowired
    ConsoleRemoteService userRemoteService;
    @Autowired
    StringRedisTemplate redisTemplate;

    public UserSimpleVO getUserById(BigInteger userId) {
        UserSimpleVO vo = new UserSimpleVO();
        Result<AppUser> userResult = userRemoteService.getUserById(userId);
        AppUser user = userResult.getData();
        vo.setHeadImage(user.getHeadImage());
        vo.setNickname(user.getNickname());
        String username = user.getCellphone().substring(0, 3) + "****" + user.getCellphone().substring(7);
        vo.setUsername(username);
        return vo;
    }

    public TokenVO login(UserDTO userDTO) {
        TokenVO vo = new TokenVO();
        Result<AppUser> userResult = userRemoteServ