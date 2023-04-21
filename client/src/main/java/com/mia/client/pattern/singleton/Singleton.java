package com.mia.client.pattern.singleton;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/20 18:09
 */
public class Singleton {
    private Singleton() {
    }

//    private final static Singleton pattern.singleton = new Singleton();
//
//    public static Singleton getInstance() {
//        return pattern.singleton;
//    }

    private volatile static Singleton singleton;

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
