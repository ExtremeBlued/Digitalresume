
package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * block_transaction
 */
@Table(name = "block_transaction")
@Data
public class BlockTransaction implements Serializable {
    /**
     * 区块链交易记录
     */
    @Id
    @Column(name = "id")
    private BigInteger id;

    /**
     * 交易hash
     */
    @Column(name = "hash")
    private String hash;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Long createdAt;

    /**
     * 修改时间
     */
    @Column(name = "updated_at")
    private Long updatedAt;

    /**
     * 手续费
     */
    @Column(name = "fee")
    private BigDecimal fee;

    /**
     * 打包区块高度
     */
    @Column(name = "height")
    private BigInteger height;

    /**
     * 令牌id
     */
    @Column(name = "token_id")
    private BigInteger tokenId;

    /**
     * 令牌id
     */
    @Column(name = "token_type")
    private String tokenType;

    /**
     * 操作类型 1充值 2提现
     */
    @Column(name = "opr_type")
    private Integer oprType;

    /**
     * 对应用户id
     */
    @Column(name = "user_id")
    private BigInteger userId;

    /**
     * 订单状态0打包中 1确认中 2确认完毕 9失败
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 交易状态1. 待审核2. 待签名（审核通过后3. 拒绝4. 正在提币（导入签名文件后5. 提币成功（交易确认成功后6. 失败")
     */
    @Column(name = "transaction_status")
    private Integer transactionStatus;

    /**
     * 错误原因
     */
    @Column(name = "error_msg")
    private String errorMsg;

    /**
     * 错误描述
     */
    @Column(name = "error_data")
    private String errorData;

    /**
     * 交易数量
     */
    @Column(name = "value")
    private BigDecimal value;

    /**
     * 来源地址
     */
    @Column(name = "from_address")
    private String fromAddress;