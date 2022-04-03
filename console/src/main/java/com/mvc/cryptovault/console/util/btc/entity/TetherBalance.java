package com.mvc.cryptovault.console.util.btc.entity;

import java.math.BigDecimal;

public class TetherBalance {
    private String address;
    private BigDecimal balance;
    private BigDecimal reserved;
    private BigDecimal frozen;

    public static TetherBalance convert(int tetherId, OmniWalletAddressBalance omniWalletAddressBalance){
        TetherBalance tetherBalance = null;
        for (OmniBalance omniBalance : omniWalletAddressBalance.getBalances()) {
            if (tetherId == omniBalance.getProper