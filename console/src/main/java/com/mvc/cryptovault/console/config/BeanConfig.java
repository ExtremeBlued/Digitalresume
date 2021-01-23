package com.mvc.cryptovault.console.config;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import com.mvc.cryptovault.common.util.JwtHelper;
import com.mvc.cryptovault.console.util.btc.BtcAction;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import lombok.Cleanup;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author qiyichen
 * @create 2018/11/13 14:44
 */
@Configuration
public class BeanConfig {

    @Value("${jpush.secret}")
    private String MASTER_SECRET;

    @Value("${jpush.app_key}")
    private String APP_KEY;

    @Value("${service.name}")
    private String serviceName;
    @Value("${service.expire}")
    private Long expire;
    @Value("${service.refresh}")
    private Long refresh;
    @Value("${service.base64Secret}")
    private String base64Secret;
    @Value("${eth.geth}")
    public String WALLET_SERVICE;
    @Value("${usdt.propId}")
    private Integer propId;

    @Bean
    public HTreeMap hTreeMap() {
        HTreeMap myCache = DBMaker.heapDB().concurrencyScale(16).make().hashMap("consoleCache")
                .expireMaxSize(10000)
                .expireAfterCreate(1, TimeUnit.HOURS)
                .expireAfterUpdate(1, TimeUnit.HOURS)
                .expireAft