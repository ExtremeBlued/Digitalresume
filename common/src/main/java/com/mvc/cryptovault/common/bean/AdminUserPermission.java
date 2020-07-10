package com.mvc.cryptovault.common.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * admin_user_permission
 */
@Table(name = "admin_user_permission")
@Data
public class AdminUserPermission implements Serializable {
  