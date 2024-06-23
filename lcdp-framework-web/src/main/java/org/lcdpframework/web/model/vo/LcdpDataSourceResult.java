package org.lcdpframework.web.model.vo;

public record LcdpDataSourceResult(
        String dataSourceId,
        String dataSourceName,
        String databaseType,
        String dataSourceUrl,
        String account,
        String password
) {
}
