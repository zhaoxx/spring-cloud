package com.mia.client.pattern.observer;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/21 16:10
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private ApplicationContext applicationContext;

    @GetMapping("/cancel")
    public String cancel(String orderNo){
        ChangeEvent event = new ChangeEvent(this, orderNo);

        applicationContext.publishEvent(event);
        return "success.";
    }
}
