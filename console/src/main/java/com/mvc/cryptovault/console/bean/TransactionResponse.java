package com.mvc.cryptovault.console.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qyc
 */
@Getter
@Setter
public class TransactionResponse<T> {

    private String transactionHash;
    private T event;

    TransactionResponse() {
    }

    public TransactionResponse(String transactionHash) {
        this(tra