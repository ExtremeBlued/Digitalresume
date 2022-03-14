
package com.mvc.cryptovault.console.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mvc.cryptovault.common.bean.*;
import com.mvc.cryptovault.common.bean.dto.PageDTO;
import com.mvc.cryptovault.common.bean.dto.PairDTO;
import com.mvc.cryptovault.common.bean.vo.OrderInfoVO;
import com.mvc.cryptovault.common.bean.vo.PairVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DTokenSettingVO;
import com.mvc.cryptovault.common.dashboard.bean.vo.DTokenTransSettingVO;
import com.mvc.cryptovault.common.util.ConditionUtil;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.constant.BusinessConstant;
import com.mvc.cryptovault.console.dao.CommonTokenHistoryMapper;
import com.mvc.cryptovault.console.dao.CommonTokenMapper;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommonTokenService extends AbstractService<CommonToken> implements BaseService<CommonToken> {

    @Autowired
    CommonTokenMapper commonTokenMapper;
    @Autowired
    CommonTokenPriceService commonTokenPriceService;
    @Autowired
    CommonPairService commonPairService;
    @Autowired
    CommonTokenHistoryMapper commonTokenHistoryMapper;
    @Autowired
    CommonTokenControlService commonTokenControlService;
    @Autowired
    AppUserBalanceService appUserBalanceService;
    @Autowired
    AppUserTransactionService appUserTransactionService;

    public List<PairVO> getPair(PairDTO pairDTO) {
        Long start = System.currentTimeMillis();
        List<PairVO> result = new ArrayList<>();
        List<CommonPair> list = commonPairService.findAll();
        if (null != pairDTO.getPairType()) {
            list = list.stream().filter(obj -> obj.getStatus() == 1 && obj.getBaseTokenId().equals(BigInteger.valueOf(pairDTO.getPairType()))).collect(Collectors.toList());
        }
        List<String> tokenIds = list.stream().map(obj -> String.valueOf("commonTokenHistory".toUpperCase() + "_24H_BEFORE" + obj.getTokenId())).collect(Collectors.toList());
        Map<BigInteger, BigDecimal> lastBefore = get24HBefore(tokenIds);
        list.forEach(obj -> {
            CommonToken token = findById(obj.getTokenId());
            CommonTokenPrice price = commonTokenPriceService.findById(obj.getTokenId());
            CommonTokenControl commonTokenControl = commonTokenControlService.findById(obj.getTokenId());
            PairVO vo = new PairVO();
            vo.setPair(obj.getPairName());
            vo.setTokenImage(token.getTokenImage());
            vo.setTokenName(token.getTokenName());
            vo.setRatio(null == price ? BigDecimal.ZERO : price.getTokenPrice());
            vo.setTokenId(token.getId());
            vo.setPairId(obj.getId());
            vo.setTransactionStatus(null == commonTokenControl ? 0 : commonTokenControl.getTransactionStatus());
            if (null == lastBefore.get(token.getId()) || lastBefore.get(token.getId()).equals(BigDecimal.ZERO)) {
                vo.setIncrease(0f);
            } else {
                BigDecimal now = null == price ? BigDecimal.ONE : price.getTokenPrice();
                Float increase = now.divide(lastBefore.get(token.getId()),10, RoundingMode.HALF_DOWN).setScale(10, RoundingMode.HALF_DOWN).floatValue() - 1;
                vo.setIncrease(increase * 100);
            }
            result.add(vo);
        });
        return result;
    }

    public BigDecimal get24HBefore(BigInteger tokenId) {
        String key = "commonTokenHistory".toUpperCase() + "_24H_BEFORE" + tokenId;
        String obj = redisTemplate.boundValueOps(key).get();
        BigDecimal price = null;
        if (StringUtils.isBlank(obj)) {
            price = update24HBeforePrice(tokenId, key);
        } else {
            price = NumberUtils.parseNumber(obj, BigDecimal.class);
        }
        return price;
    }

    public Map<BigInteger, BigDecimal> get24HBefore(List<String> tokenIds) {
        Map<BigInteger, BigDecimal> result = new HashMap<>(tokenIds.size());
        List<String> list = redisTemplate.opsForValue().multiGet(tokenIds);
        for (int i = 0; i < list.size(); i++) {
            BigInteger tokenId = NumberUtils.parseNumber(tokenIds.get(i).substring(29), BigInteger.class);
            if (StringUtils.isBlank(list.get(i))) {
                String key = "commonTokenHistory".toUpperCase() + "_24H_BEFORE" + tokenId;
                BigDecimal price = update24HBeforePrice(tokenId, key);
                price = price == null ? BigDecimal.ZERO : price;
                redisTemplate.opsForValue().set(key, String.valueOf(price));
                result.put(tokenId, price);
            } else {
                BigDecimal price = NumberUtils.parseNumber(list.get(i), BigDecimal.class);
                result.put(tokenId, price);
            }
        }
        return result;
    }

    @Nullable
    public BigDecimal update24HBeforePrice(BigInteger tokenId, String key) {
        PageHelper.clearPage();
        BigDecimal price = commonTokenHistoryMapper.get24HBefore(tokenId, System.currentTimeMillis());
        if (null == price) {
            price = commonTokenHistoryMapper.getFirst(tokenId);
        }
        if (null != price) {
            redisTemplate.boundValueOps(key).set(String.valueOf(price));
        }
        return price;
    }

    public OrderInfoVO getInfo(BigInteger userId, BigInteger pairId, Integer transactionType, BigInteger id) {
        OrderInfoVO vo = new OrderInfoVO();
        CommonPair pair = commonPairService.findById(pairId);
        CommonTokenPrice price = commonTokenPriceService.findById(pair.getTokenId());
        CommonTokenControl tokenControl = commonTokenControlService.findById(pair.getTokenId());
        tokenControl = tokenControl == null ? new CommonTokenControl() : tokenControl;
        vo.setBalance(appUserBalanceService.getBalanceByTokenId(userId, pair.getBaseTokenId()));
        vo.setTokenBalance(appUserBalanceService.getBalanceByTokenId(userId, pair.getTokenId()));
        vo.setPrice(null == price ? BigDecimal.ZERO : price.getTokenPrice());
        vo.setMinLimit(null == tokenControl ? BigDecimal.ZERO : tokenControl.getMinLimit());
        if (null != id && !BigInteger.ZERO.equals(id)) {
            AppUserTransaction trans = appUserTransactionService.findById(id);
            vo.setValue(trans.getValue().subtract(trans.getSuccessValue()));
        } else {
            vo.setValue(BigDecimal.ZERO);
        }
        if (transactionType.equals(BusinessConstant.TRANSACTION_TYPE_BUY)) {
            vo.setMin(tokenControl.getBuyMin());
            vo.setMax(tokenControl.getBuyMax());
        } else {
            vo.setMin(tokenControl.getSellMin());
            vo.setMax(tokenControl.getSellMax());
        }
        return vo;
    }

    public DTokenSettingVO getTokenSetting(BigInteger id) {
        DTokenSettingVO result = new DTokenSettingVO();
        CommonToken token = findById(id);
        BeanUtils.copyProperties(token, result);
        List<CommonPair> list = commonPairService.findBy("tokenId", id);
        Long vrt = list.stream().filter(obj -> obj.getBaseTokenId().equals(BusinessConstant.BASE_TOKEN_ID_VRT) && obj.getStatus() == 1).count();
        Long balance = list.stream().filter(obj -> obj.getBaseTokenId().equals(BusinessConstant.BASE_TOKEN_ID_BALANCE) && obj.getStatus() == 1).count();
        result.setVrt(vrt.intValue());
        result.setBalance(balance.intValue());
        result.setInner(StringUtils.isBlank(token.getTokenContractAddress()) && (null == token.getTokenDecimal() || 0 == token.getTokenDecimal()) ? 1 : 0);
        return result;
    }

    public DTokenTransSettingVO getTransSetting(BigInteger id) {
        DTokenTransSettingVO result = new DTokenTransSettingVO();
        CommonTokenControl token = commonTokenControlService.findById(id);
        if (null == token) {
            result.setTokenId(id);
            return result;
        }
        BeanUtils.copyProperties(token, result);
        return result;
    }

    public PageInfo<DTokenSettingVO> getTokenSettings(PageDTO pageDTO, String tokenName) {
        Condition condition = new Condition(CommonToken.class);
        Example.Criteria criteria = condition.createCriteria();
        PageHelper.startPage(pageDTO.getPageSize(), pageDTO.getPageNum(), pageDTO.getOrderBy());
        ConditionUtil.andCondition(criteria, "token_name = ", tokenName);
        ConditionUtil.andCondition(criteria, "created_at >= ", pageDTO.getCreatedStartAt());
        ConditionUtil.andCondition(criteria, "created_at <= ", pageDTO.getCreatedStopAt());
        List<CommonToken> list = findByCondition(condition);
        List<DTokenSettingVO> vos = new ArrayList<>(list.size());
        for (CommonToken commonToken : list) {
            DTokenSettingVO vo = new DTokenSettingVO();
            BeanUtils.copyProperties(commonToken, vo);
            List<CommonPair> data = commonPairService.findBy("tokenId", commonToken.getId());
            Long vrt = data.stream().filter(obj -> obj.getBaseTokenId().equals(BusinessConstant.BASE_TOKEN_ID_VRT) && obj.getStatus() == 1).count();
            Long balance = data.stream().filter(obj -> obj.getBaseTokenId().equals(BusinessConstant.BASE_TOKEN_ID_BALANCE) && obj.getStatus() == 1).count();
            vo.setVrt(vrt.intValue());
            vo.setBalance(balance.intValue());
            vos.add(vo);
        }
        PageInfo result = new PageInfo(list);
        result.setList(vos);
        return result;
    }

    public void setTransSetting(DTokenTransSettingVO dto) {
        CommonTokenControl tokenControl = commonTokenControlService.findById(dto.getTokenId());
        CommonToken token = findById(dto.getTokenId());
        //开启过一次以后开盘价格不能变化
        if (null == tokenControl && null != token) {
            tokenControl = new CommonTokenControl();
            BeanUtils.copyProperties(dto, tokenControl);
            if (tokenControl.getTransactionStatus() == 1) {
                tokenControl.setStartStatus(1);
            } else {
                tokenControl.setStartStatus(0);
            }
            commonTokenControlService.save(tokenControl);
            //创建初始化k线数据
            commonTokenPriceService.init(tokenControl);
        } else {
            Integer startStatus = tokenControl.getStartStatus();
            tokenControl = new CommonTokenControl();
            BeanUtils.copyProperties(dto, tokenControl);
            tokenControl.setStartStatus(startStatus);
            if (startStatus == 1) {
                tokenControl.setStartPrice(null);
            }
            if (startStatus == 0 && tokenControl.getTransactionStatus() == 1) {
                tokenControl.setStartStatus(1);
            }
            commonTokenControlService.update(tokenControl);
        }
        commonTokenControlService.updateCache(tokenControl.getTokenId());
        commonTokenControlService.updateAllCache();
    }

    public void tokenSetting(DTokenSettingVO dto) {
        CommonToken token = new CommonToken();
        BeanUtils.copyProperties(dto, token);
        update(token);
        commonPairService.updatePair(dto.getId(), dto.getVrt(), dto.getBalance());
    }

    public String getTokenName(BigInteger tokenId) {
        CommonToken token = findById(tokenId);
        return token.getTokenName();
    }

    public List<CommonToken> findKlineToken() {
        return commonTokenMapper.findKlineToken();
    }
}