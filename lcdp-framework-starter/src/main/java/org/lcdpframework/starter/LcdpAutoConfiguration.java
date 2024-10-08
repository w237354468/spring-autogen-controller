package org.lcdpframework.starter;

import org.lcdpframework.starter.register.UrlMappingAutoRegistryListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Configuration
@ComponentScan("org.lcdpframework")
@Import({DynamicDataSourceAutoConfigure.class, UrlMappingAutoRegistryListener.class})
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class LcdpAutoConfiguration {
}
