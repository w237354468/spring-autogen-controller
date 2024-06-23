package org.lcdpframework.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "lcdp.default")
public class LcdpFieldConfiguration {

    private String delField = "";
}
