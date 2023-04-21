package com.mia.gateway.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

import static com.alibaba.nacos.api.PropertyKeyConst.ENDPOINT_PORT;
import static com.alibaba.nacos.api.annotation.NacosProperties.*;

@Component
@Slf4j
public class NacosDynamicRouteService implements ApplicationEventPublisherAware {

    /* 服务地址 */
    @Value("${nacos.config.server-addr:}")
    private String serverAddr;

    /* 命名空间 */
    @Value("${nacos.config.namespace:}")
    private String namespace;

    /**
     * encode for nacos config content.
     */
    private String encode = "UTF-8";

    /**
     * nacos maximum number of tolerable server reconnection errors.
     */
    private String maxRetry = "3";

    /**
     * nacos get config long poll timeout.
     */
    private String configLongPollTimeout;

    /**
     * nacos get config failure retry time.
     */
    private String configRetryTime;

    /**
     * If you want to pull it yourself when the program starts to get the configuration
     * for the first time, and the registered Listener is used for future configuration
     * updates, you can keep the original code unchanged, just add the system parameter:
     * enableRemoteSyncConfig = "true" ( But there is network overhead); therefore we
     * recommend that you use {@link ConfigService#getConfigAndSignListener} directly.
     */
    private boolean enableRemoteSyncConfig = false;

    /**
     * endpoint for Nacos, the domain name of a service, through which the server address
     * can be dynamically obtained.
     */
    @Value("${nacos.config.endpoint:}")
    private String endpoint;

    /**
     * access key for namespace.
     */
    @Value("${nacos.config.username:}")
    private String username;

    /**
     * secret key for namespace.
     */
    @Value("${nacos.config.password:}")
    private String password;

    /**
     * context path for nacos config server.
     */
    private String contextPath;

    /**
     * nacos config cluster name.
     */
    private String clusterName;
//
    @Value("${nacos.config.dataId:}")
    private String dataId = "gateway-router";

    @Value("${nacos.config.group:}")
    private String group = "api-gateway";

    private ConfigService configService;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher applicationEventPublisher;

    private static final List<String> ROUTE_LIST = new ArrayList<>();

    /**
     * 组装配置参数
     * @return
     */
    public Properties assembleConfigServiceProperties() {
        Properties properties = new Properties();
        properties.put("serverAddr", Objects.toString(this.serverAddr, ""));
        properties.put(ENCODE, Objects.toString(this.encode, ""));
        properties.put(NAMESPACE, Objects.toString(this.namespace, ""));
        properties.put(USERNAME, Objects.toString(this.username, ""));
        properties.put(PASSWORD, Objects.toString(this.password, ""));
        properties.put(CONTEXT_PATH, Objects.toString(this.contextPath, ""));
        properties.put(CLUSTER_NAME, Objects.toString(this.clusterName, ""));
        properties.put(MAX_RETRY, Objects.toString(this.maxRetry, ""));
        properties.put(CONFIG_LONG_POLL_TIMEOUT,
                Objects.toString(this.configLongPollTimeout, ""));
        properties.put(CONFIG_RETRY_TIME, Objects.toString(this.configRetryTime, ""));
        properties.put(ENABLE_REMOTE_SYNC_CONFIG,
                Objects.toString(this.enableRemoteSyncConfig, ""));
        String endpoint = Objects.toString(this.endpoint, "");
        if (endpoint.contains(":")) {
            int index = endpoint.indexOf(":");
            properties.put(ENDPOINT, endpoint.substring(0, index));
            properties.put(ENDPOINT_PORT, endpoint.substring(index + 1));
        } else {
            properties.put(ENDPOINT, endpoint);
        }

        return properties;
    }

    @PostConstruct
    public void initRoute() {
        try{
            configService = NacosFactory.createConfigService(this.assembleConfigServiceProperties());
            if(configService == null){
                log.warn("initConfigService fail");
                return;
            }
            String configInfo = configService.getConfig(dataId, group, 5000);
            log.info("获取网关当前配置:\r\n{}",configInfo);

            List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
            for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
                log.info("update route : {}",routeDefinition.toString());
                addRoute(routeDefinition);
            }
            publish();
        } catch (Exception e) {
            log.error("初始化网关路由时发生错误",e);
        }
        this.dynamicRouteByNacosListener();
    }

    /**
     * 监听网关路由
     */
    private void dynamicRouteByNacosListener() {
        try {
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    clearRoute();
                    try {
                        List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(configInfo.toString(), RouteDefinition.class);
                        for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
                            log.info("update route : {}",routeDefinition.toString());
                            addRoute(routeDefinition);
                        }
                        publish();
                    } catch (Exception e) {
                        log.error("监听处理网关路由时发生错误",e);
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("初始化网关路由监听时错误",e);
        }
    }

    private void clearRoute() {
        for (String id : ROUTE_LIST) {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_LIST.clear();
    }

    private void addRoute(RouteDefinition definition) {
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            ROUTE_LIST.add(definition.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getEncode() {
        return encode;
    }

    public String getMaxRetry() {
        return maxRetry;
    }

    public String getConfigLongPollTimeout() {
        return configLongPollTimeout;
    }

    public String getConfigRetryTime() {
        return configRetryTime;
    }

    public boolean isEnableRemoteSyncConfig() {
        return enableRemoteSyncConfig;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getDataId() {
        return dataId;
    }

    public String getGroup() {
        return group;
    }
}
