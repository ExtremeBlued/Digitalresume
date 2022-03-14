
package com.mvc.cryptovault.console.service;

import com.mvc.cryptovault.console.bean.HumanStandardToken;
import com.mvc.cryptovault.console.bean.NodeConfiguration;
import com.mvc.cryptovault.console.bean.TransactionResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

/**
 * @author qyc
 */
@Service
public class ContractService {

    private final Quorum quorum;

    private final NodeConfiguration nodeConfiguration;

    @Autowired
    public ContractService(Quorum quorum, NodeConfiguration nodeConfiguration) {
        this.quorum = quorum;
        this.nodeConfiguration = nodeConfiguration;
    }

    public NodeConfiguration getConfig() {
        return nodeConfiguration;
    }

    public String deploy(
            List<String> privateFor, long initialAmount, String tokenName, long decimalUnits,
            String tokenSymbol) throws IOException, TransactionException {
        TransactionManager transactionManager = new ClientTransactionManager(
                quorum, nodeConfiguration.getFromAddress(), privateFor);
        HumanStandardToken humanStandardToken = HumanStandardToken.deploy(
                quorum, transactionManager, GAS_PRICE, GAS_LIMIT.divide(BigInteger.valueOf(100)), BigInteger.ZERO,
                new Uint256(initialAmount), new Utf8String(tokenName), new Uint8(decimalUnits),
                new Utf8String(tokenSymbol));
        return humanStandardToken.getContractAddress();
    }

    public String name(String contractAddress) {
        HumanStandardToken humanStandardToken = load(contractAddress);
        try {
            return extractValue(humanStandardToken.name().get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TransactionResponse<ApprovalEventResponse> approve(
            List<String> privateFor, String contractAddress, String spender, long value) {
        HumanStandardToken humanStandardToken = load(contractAddress, privateFor);
        try {
            TransactionReceipt transactionReceipt = humanStandardToken
                    .approve(new Address(spender), new Uint256(value));
            return processApprovalEventResponse(humanStandardToken, transactionReceipt);
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long totalSupply(String contractAddress) {
        HumanStandardToken humanStandardToken = load(contractAddress);
        try {
            return extractLongValue(humanStandardToken.totalSupply().get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public TransactionResponse<TransferEventResponse> transferFrom(List<String> privateFor, String contractAddress, String from, String to, long value) {
        HumanStandardToken humanStandardToken = load(contractAddress, privateFor);
        try {
            TransactionReceipt transactionReceipt = humanStandardToken
                    .transferFrom(new Address(from), new Address(to), new Uint256(value));

            System.out.println(transactionReceipt.getTransactionHash());
            return processTransferEventsResponse(humanStandardToken, transactionReceipt);
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long decimals(String contractAddress) {
        HumanStandardToken humanStandardToken = load(contractAddress);
        try {
            return extractLongValue(humanStandardToken.decimals().get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String version(String contractAddress) {
        HumanStandardToken humanStandardToken = load(contractAddress);
        try {
            return extractValue(humanStandardToken.version().get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }