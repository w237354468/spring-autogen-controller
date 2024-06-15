package org.lcdpframework.web.model.qo;

public record LcdpDataSourceUpdate(
        String dataSourceName,
        String databaseType,
        String dataSourceUrl,
        String account,
        String password
) {
}
