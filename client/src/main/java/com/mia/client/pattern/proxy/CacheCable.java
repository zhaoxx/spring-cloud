package com.mia.client.pattern.proxy;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheCable {

    String[] value();

    String key();

    /**
     * 过期时间单位：s（秒）
     *
     * @return
     */
    long expiration() default 0;
}
