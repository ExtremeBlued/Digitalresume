
package com.mvc.cryptovault.console;

import com.alibaba.fastjson.JSONObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author qiyichen
 * @create 2018/11/30 18:11
 */
public class tEST {

    public static void main(String[] args) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String usdtUrl = "http://query.yahooapis.com/v1/public/yql?q=select data.price from json where url=\"https://data.block.cc/api/v1/price?symbol=USDT\"&format=json";
        JSONObject result = restTemplate.getForObject(usdtUrl, JSONObject.class);
        System.out.println(result);

//        Web3j web3j = Web3j.build(new HttpService("http://47.75.14.213:8545", okHttpClient(), false));
//        BigInteger pvKey = new BigInteger("88726939231405558771505325775688474272950033802795314809859034910058460564307");
//        //c429aaff49b96dd56eab31deb79fd8d602a99466fffe086bcf943bc507cd4b53
//        System.out.println(pvKey.toString(16));
//        String address = "0xa0941c373aeca2e96f439b075354f9328f6442c1";
//        String to = "0x9905eac197c192060cd6f0d54357d08eff7c646c";
//        BigInteger nonce = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send().getTransactionCount();
//        ECKeyPair ecKeyPair = ECKeyPair.create(pvKey);
//        Credentials ALICE = Credentials.create(ecKeyPair);
//        RawTransaction transaction = RawTransaction.createEtherTransaction(nonce, Convert.toWei("2", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(21000), to, Convert.toWei("0.5", Convert.Unit.ETHER).toBigInteger());
////        byte[] signedMessage = TransactionEncoder.signMessage(transaction, ALICE);
////        String hexValue = Numeric.toHexString(signedMessage);
////        EthSendTransaction result = web3j.ethSendRawTransaction(hexValue).send();
////        System.out.println(result);
    }

    public static OkHttpClient okHttpClient() throws IOException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request requestWithUserAgent = originalRequest.newBuilder().build();
                        return chain.proceed(requestWithUserAgent);
                    }
                });
        return builder.build();
    }

}