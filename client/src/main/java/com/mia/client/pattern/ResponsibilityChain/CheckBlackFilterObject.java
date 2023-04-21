package com.mia.client.pattern.ResponsibilityChain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sun.misc.Request;

import javax.xml.ws.Response;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/20 16:55
 */
@Component
@Order(3)
public class CheckBlackFilterObject extends AbstractHandler {
    @Override
    public void doFilter(Request request, Response response) {
        System.out.println("校验黑名单");
    }
}