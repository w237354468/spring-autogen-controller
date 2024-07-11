package org.lcdpframework.server.util;

import org.lcdpframework.server.event.LcdpApplicationEvent;
import org.lcdpframework.server.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import static org.lcdpframework.server.log.Log.LOGGER_TYPE.SYSTEM;

@Component
public class LcdpEventUtil implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(LcdpEventUtil.class);

    public static ApplicationContext applicationContext;

    public static void publishEvent(LcdpApplicationEvent event) {
        if (applicationContext == null) {
            logger.error("application context hasn't initialized yet");
            return;
        }
        Log.info(SYSTEM, "publish event with {}", event.getClass().getSimpleName());
        applicationContext.publishEvent(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LcdpEventUtil.applicationContext = applicationContext;
    }
}
