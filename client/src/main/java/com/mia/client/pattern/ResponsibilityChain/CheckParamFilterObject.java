package com.mia.client.pattern.ResponsibilityChain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sun.misc.Request;

import javax.xml.ws.Response;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/20 16:53
 */
@Component
@Order(1) //顺序排第1，最先校验
public class CheckParamFilterObject extends AbstractHandler {
    @Override
    public void doFilter(Request request, Response response) {
        System.out.println("参数检查");
    }
}
