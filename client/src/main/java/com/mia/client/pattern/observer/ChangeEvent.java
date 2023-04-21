package com.mia.client.pattern.observer;

import org.springframework.context.ApplicationEvent;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/21 15:10
 */
public class ChangeEvent extends ApplicationEvent {

    private String orderNo;

    public ChangeEvent(Object source, String orderNo) {
        super(source);
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
