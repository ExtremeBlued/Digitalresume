package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * common_token_control_next
 */
@Table(name = "common_token_control_next")
@Data
public class CommonTokenControlNext implements Serializable {
    /**
     * 
     */
    @Id
    @Column(name = "token_id")
    private BigInteger tokenId;

 