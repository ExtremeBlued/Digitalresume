package com.mvc.cryptovault.app.service;

import com.mvc.cryptovault.app.feign.ConsoleRemoteService;
import com.mvc.cryptovault.common.bean.dto.AssertVisibleDTO;
import com.mvc.cryptovault.common.bean.dto.DebitDTO;
import com.mvc.cryptovault.common.bean.dto.TransactionDTO;
import com.mvc.cryptovault.common.bean.dto.TransactionSearchDTO;
import com.mvc.cryptovault.common.bean.vo.*;
import com.mvc.cryptovault.common.util.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class AssetService {

    @Autowired
    ConsoleRemoteService consoleRemoteService;
    private final static String USDT_REG_ARR = "^[123mn][a-zA-Z1-9]{24,34}$";
    private final static String ETH_REG = "^(0x)?[0-9a-fA-F]{40}$";

    public List<TokenBalanceVO> getAsset(BigInteger userId) {
        Result<List<TokenBalanceVO>> result = consoleRemoteService.getAsset(userId);
        return result.getData();
    }

    public BigDecimal getBalance(BigInteger userId) {
        Result<BigDecimal> result = consoleRemoteService.getBalance(userId);
        return result.getData();
    }

    public List<TransactionSimpleVO> getTransactions(BigInteger userId, TransactionSearchDTO transactionSearchDTO) {
        Result<List<TransactionSimpleVO>> result = consoleRemoteService.getTransactions(userId, transactionSearchDTO.getTransactionType(), transactionSearchDTO.getId(), transactionSearchDTO.getType(), transactionSearchDTO.getPageSize(), transactionSearchDTO.getTokenId());
        return result.getData();
    }

    public TransactionDetailVO getTransaction(BigInteger userId, BigInteger