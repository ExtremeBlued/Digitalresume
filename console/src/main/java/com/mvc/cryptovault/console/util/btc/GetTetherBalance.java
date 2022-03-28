package com.mvc.cryptovault.console.util.btc;

import com.mvc.cryptovault.console.util.btc.entity.TetherBalance;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;

import java.io.IOException;
import java.util.List;

public class GetTetherBalance extends BtcAction {

    private static String address;

    public static void main(String[] args) throws BitcoindException, IOException, CommunicationException {
        parseArgs(args);
        // Print
        if (address == null) {
            List<TetherBalance> tetherBalanceList = getTetherBalance