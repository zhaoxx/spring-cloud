package com.mia.gateway;

import com.mia.gateway.config.NacosDynamicRouteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.TimeZone;

@SpringBootApplication
//@EnableDiscoveryClient(autoRegister=false)
@EnableEurekaClient
public class GatewayApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        ConfigurableApplicationContext context = SpringApplication.run(GatewayApplication.class, args);
//        NacosDynamicRouteService nacosDynamicRouteService = context.getBean(NacosDynamicRouteService.class);
//        nacosDynamicRouteService.initRoute();
    }
}
