package com.mia.client.principle;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/21 9:10
 */
public class SingleResponsibility {

}

interface OrderService {
    void createOrder(Object obj);
    void pay(Object obj);
    void cancelOrder(String orderNo);
}


//interface OrderService {
//    void createOrder(Object obj);
//    void cancelOrder(String orderNo);
//}
//interface PayService {
//    void pay(Object obj);
//}

