package org.lcdpframework.starter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("org.lcdpframework")
@Import({DynamicDataSourceAutoConfigure.class})
public class LcdpAutoConfiguration {
}
