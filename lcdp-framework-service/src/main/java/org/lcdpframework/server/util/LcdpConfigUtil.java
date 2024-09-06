package org.lcdpframework.server.util;

import org.lcdpframework.server.config.LcdpFieldConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class LcdpConfigUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public static String getDefaultDeleteField() {
        LcdpFieldConfiguration bean = applicationContext.getBean(LcdpFieldConfiguration.class);
        return bean.getDelField();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
