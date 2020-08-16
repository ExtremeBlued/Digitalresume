package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * block_height
 */
@Table(name = "block_height")
@Data
public class BlockHeight implements Serializable {
    /**
     * 令牌id
     */
    @Id

    @Column(name = "token_id")
    private BigInteger tokenId;

    /**
     * 当前高度
     */
    @Column(name = "height")
    private Integer height;

    private BigDecimal fee;

    private Big