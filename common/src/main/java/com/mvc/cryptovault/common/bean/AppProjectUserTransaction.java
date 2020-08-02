package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * app_project_user_transaction
 */
@Table(name = "app_project_user_transaction")
@Data
public class AppProjectUserTransaction implements Serializable {
    /**
     * 
     */
    @Id
    @Column(name = "id")
    private BigInteger id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private BigInteger userId;

    /**
     * 项目id
     */
    @Column(name = "project_id")
    private BigInteger projectId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Long createdAt;

    /**
     * 修改时间
     */
    @Column(name = "updated