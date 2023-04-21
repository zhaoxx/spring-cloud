package com.mia.server.web;

import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class PrometheusMeterManager {

    private static PrometheusMeterRegistry registry = null;

    public static void init() {
        registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }

    public static PrometheusMeterRegistry register() {
        if (registry == null) {
            init();
        }
        return registry;
    }
}
