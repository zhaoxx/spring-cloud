package com.mia.gateway.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * 标准输出日志过滤
 */
public class StatisticsLogFilter extends AbstractMatcherFilter<ILoggingEvent> {

    Level level;

    public StatisticsLogFilter() {
    }

    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().equals("statisticsLog")) {
            return FilterReply.DENY;
        } else {
            if (!this.isStarted()) {
                return FilterReply.NEUTRAL;
            } else {
                return event.getLevel().equals(this.level) ? this.onMatch : this.onMismatch;
            }
        }
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void start() {
        if (this.level != null) {
            super.start();
        }

    }
}
