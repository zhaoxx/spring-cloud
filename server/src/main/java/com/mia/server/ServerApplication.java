package com.mia.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    /*@Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName){
        return registry -> registry.config().commonTags("application", applicationName);
    }

    @NotNull
    @Bean
    ServletListenerRegistrationBean<ServletContextListener> myServletListener() {
        ServletListenerRegistrationBean<ServletContextListener> srb =
                new ServletListenerRegistrationBean<>();
        srb.setListener(new ExampleServletContextListener());
        return srb;
    }


    public class ExampleServletContextListener implements ServletContextListener {
        @Override
        public void contextInitialized(
                ServletContextEvent sce) {
            // Context Initialised
        }

        @Override
        public void contextDestroyed(
                ServletContextEvent sce) {
            DiscoveryManager.getInstance().shutdownComponent();
            // Here - what you want to do that context shutdown
            System.out.println("==============调用了shutdown后输出==================");
        }
    }*/
}
