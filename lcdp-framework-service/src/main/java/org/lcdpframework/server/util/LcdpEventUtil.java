package org.lcdpframework.server.util;

import org.lcdpframework.server.event.LcdpApplicationEvent;
import org.lcdpframework.server.log.LcdpLog;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static org.lcdpframework.server.log.LcdpLog.LOGGER_TYPE.SYSTEM;

public class LcdpEventUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static void publishEvent(LcdpApplicationEvent event) {
        LcdpLog.printInfo(SYSTEM, "pbulish event with", event.getClass().getSimpleName());
        applicationContext.publishEvent(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
