package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * common_pair
 */
@Table(name = "common_pair")
@Data
public class CommonPair implements Serializable {
    /**
     * 交易对id
     */
    @Id

    @Column(nam