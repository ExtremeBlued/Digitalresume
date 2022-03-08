package com.mvc.cryptovault.console.service;

import com.mvc.cryptovault.common.bean.AppUser;
import com.mvc.cryptovault.common.bean.BlockSign;
import com.mvc.cryptovault.common.constant.RedisConstant;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.dao.BlockSignMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * @author qiyichen
 * @create 2018/12/3 14:05
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class BlockSignService extends AbstractService<BlockSign> implements BaseService<BlockSign> {
    @Autowired
    BlockSignMapper blockSignMapper;
    @Autowired
    AppUserService appUserService;

    public BlockSign findOneByToken(String tokenType) {
        BlockSign sign = blockSignMapper.findOneByToken(tokenType, System.currentTimeMillis());
        return sign;
    }

    @Async
    public void importSign(List<BlockSign> list, String fileName) {
        String key = RedisConstant.TRANS_IMPORT + fileName;
        for (BlockSign blockSign : list) {
            if (blockSign.getTokenType().equalsIgnoreCase("BTC")) {
                blockSign.setContractAddress(null);
            }
            save(blockSign);
        }
        //导入成功后删除标记
        redisTemplate.delete(key);
    }

    @Async
    public void importAppUser(List<AppUser> list, String fileName) {
        Long time = System.currentTimeMillis();
        for (AppUser user : list) {
            try {
                AppUser temp = appUserService.findOneBy("vpUserId", user.getVpUserId());
                if (null == temp) {
           