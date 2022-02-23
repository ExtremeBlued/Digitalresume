
package com.mvc.cryptovault.console.service;

import com.github.pagehelper.PageHelper;
import com.mvc.cryptovault.common.bean.AdminWallet;
import com.mvc.cryptovault.common.bean.BlockHeight;
import com.mvc.cryptovault.common.bean.CommonAddress;
import com.mvc.cryptovault.common.constant.RedisConstant;
import com.mvc.cryptovault.console.common.AbstractService;
import com.mvc.cryptovault.console.common.BaseService;
import com.mvc.cryptovault.console.dao.BlockHeightMapper;
import com.mvc.cryptovault.console.dao.CommonAddressMapper;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;

import java.math.BigDecimal;
import java.math.BigInteger;