package com.mvc.cryptovault.console.util.btc.entity;

import java.math.BigDecimal;

public class OmniBalance {
    private Integer propertyid;
    private String name;
    private BigDecimal balance;
    private BigDecimal reserved;
    private BigDecimal frozen;

    @Override
    public String toString() {
        return "OmniBalance{" +
                "propertyid=" + propertyid +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", reserved=" + reserved +
                ", frozen=" + frozen +
                '}';
    }

    public Integer getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(Integer propertyid) {
        this.propertyid = propertyid;
    }

    p