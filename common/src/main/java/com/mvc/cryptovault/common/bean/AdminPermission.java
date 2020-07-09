package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * admin_permission
 */
@Table(name = "admin_permission")
@Data
public class AdminPermission implements Serializable {
    /**
     * 
     */
    @Id
    @Column(name = "id")

    private BigInteger id;

    /**
     * 权限key
     */
    @Column(name = "permission_key")
    private String permissionKey;

    /**
     * 权限名称
     */
    @Column(name = "permission_name")
    private String permissionName;

    /**
     * admin_permission
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public BigInteger getId() {
        return id;