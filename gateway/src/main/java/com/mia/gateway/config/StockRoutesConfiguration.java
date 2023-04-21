//package com.mia.gateway.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by Aguan on 2020/7/22
// */
//@Configuration
//@Slf4j
//public class StockRoutesConfiguration {
//    /**
//     * @param builder
//     * @return
//     */
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        log.info("StockRoutesConfiguration filtet +++++");
//        return builder.routes()
//                .route(r -> r.path("/server/**")
//                        .filters(f -> f.stripPrefix(1)
//                        .filters(new GatewayStockFilter()))
//                        .uri("lb://stock-service-api")).build();
//    }
//}
//
