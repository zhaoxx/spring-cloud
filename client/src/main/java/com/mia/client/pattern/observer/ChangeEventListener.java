package com.mia.client.pattern.observer;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/21 15:12
 */
@Component
public class ChangeEventListener implements ApplicationListener<ChangeEvent> {
//    @EventListener
////    public void receiveMsg(OrderStatusChangeEvent event) {
////        System.out.println("接受消息：" + JSON.toJSONString(event));
////    }

    @Override
    public void onApplicationEvent(ChangeEvent orderStatusChangeEvent) {
        System.out.println("消息" + JSON.toJSONString(orderStatusChangeEvent));
    }
}


