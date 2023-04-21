package com.mia.client.pattern.ResponsibilityChain;

import sun.misc.Request;

import javax.xml.ws.Response;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/20 16:52
 */
public abstract class AbstractHandler {
    //责任链中的下一个对象
    private AbstractHandler nextHandler;

    /**
     * 责任链的下一个对象
     */
    public void setNextHandler(AbstractHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    /**
     * 具体参数拦截逻辑,给子类去实现
     */
    public void filter(Request request, Response response) {
        doFilter(request, response);
        if (getNextHandler() != null) {
            getNextHandler().filter(request, response);
        }
    }

    public AbstractHandler getNextHandler() {
        return nextHandler;
    }

    abstract void doFilter(Request filterRequest, Response response);
}
