package org.lcdpframework.server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class LcdpDataSourceDTO extends PageQuery implements Serializable {
    private String dataSourceName;
    private String databaseType;
    private String dataSourceUrl;
    private String account;
    private String password;
}
