package com.mia.client.pattern.ResponsibilityChain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sun.misc.Request;

import javax.xml.ws.Response;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/20 16:54
 */
@Component
@Order(2)
public class CheckSecurityFilterObject extends AbstractHandler {
    @Override
    public void doFilter(Request request, Response response) {
        System.out.println("安全调用校验");
    }
}
