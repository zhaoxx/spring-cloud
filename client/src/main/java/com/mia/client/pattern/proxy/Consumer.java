package com.mia.client.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/20 15:05
 */

interface IMaster {
    /**
     * 出售
     */
    void sell();
}

class Master implements IMaster {
    @Override
    public void sell() {
        System.out.println("出售");
    }
}


interface ITeacher {
    /**
     * 教书
     */
    void teach();
}

class Teacher implements ITeacher {
    @Override
    public void teach() {
        System.out.println("教书育人");
    }
}

/**
 * 静态代理
 */
class StaticProxy implements IMaster {

    private IMaster target;

    public StaticProxy(IMaster target) {
        this.target = target;
    }

    @Override
    public void sell() {
        System.out.println("静态代理开始。。do something...");
        target.sell();
        System.out.println("静态代理提交");
    }
}
//public class Consumer {
//    public static void main(String[] args) {
//        Master target = new Master();
//        StaticProxy proxyInstance = new StaticProxy(target);
//        System.out.println("proxyInstance=" + proxyInstance.getClass());
//        proxyInstance.sell();
//    }
//}

/**
 * 动态代理
 */
class DynamicFactory {
    private Object target;
    public DynamicFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("动态代理开始。。do something...");
                        Object returnVal = method.invoke(target, args);
                        System.out.println("动态代理提交");
                        return returnVal;
                    }
                });
    }
}

public class Consumer {
    public static void main(String[] args) {
        Master target = new Master();
        IMaster proxyInstance = (IMaster) new DynamicFactory(target).getProxyInstance();
        System.out.println("proxyInstance=" + proxyInstance.getClass());
        proxyInstance.sell();

        Teacher target2 = new Teacher();
        ITeacher proxyInstance2 = (ITeacher) new DynamicFactory(target2).getProxyInstance();
        System.out.println("proxyInstance2=" + proxyInstance.getClass());
        proxyInstance2.teach();
    }
}

