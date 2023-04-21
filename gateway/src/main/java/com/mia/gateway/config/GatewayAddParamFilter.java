package com.mia.gateway.config;

import com.mia.gateway.util.IPUtil;
import com.mia.gateway.util.MiaJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by Aguan on 2020/7/22
 */
@Slf4j
@Component
public class GatewayAddParamFilter implements GlobalFilter, Ordered {

    private static final String IP_HEADER_CLIENTHOSTIP = "clientHostIp";

    private final static Logger statisticsLog = LoggerFactory.getLogger("statisticsLog");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = IPUtil.getRealIpAddress(exchange.getRequest());
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(IP_HEADER_CLIENTHOSTIP, new String[]{ip})
                .build();
        exchange.getAttributes().put("record-log-start-time", System.currentTimeMillis());
        // 记录日志
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute("record-log-start-time");
            if (startTime != null) {
                Long executeTime = (System.currentTimeMillis() - startTime);
                recordLog(exchange.getRequest(), exchange.getResponse(), executeTime);
            }
        }));


    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 记录到请求日志中去
     *
     * @param request request
     */
    private void recordLog(ServerHttpRequest request, ServerHttpResponse response, Long executeTime) {
        try{
            ResponseLogMsg responseLogMsg = new ResponseLogMsg();
            responseLogMsg.setRequestUrl(request.getURI().getRawPath());
            // 记录访问的方法
            HttpMethod method = request.getMethod();
            if (null != method) {
                responseLogMsg.setMethod(method.name());
            }
            responseLogMsg.setClientHostIp(StringUtils.join(request.getHeaders().get(IP_HEADER_CLIENTHOSTIP), ","));
            responseLogMsg.setContentType(StringUtils.join(request.getHeaders().get("Content-Type"), ","));
            responseLogMsg.setResponseStatus(response.getStatusCode().value());
            responseLogMsg.setCostTime(executeTime);
            statisticsLog.info(MiaJsonUtils.getJsonValue(responseLogMsg));

            if(response.getStatusCode().value()!=200){
                //错误请求记录日志
                log.error(MiaJsonUtils.getJsonValue(responseLogMsg));
            }
        }catch(Exception e){
            log.error("记录gateway响应日志异常",e);
        }

    }
}
