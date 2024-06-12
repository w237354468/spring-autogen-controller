package org.lcdpframework.web.model.qo;

public record LcdpDataSourceQuery(
        String dataSourceName,
        Integer pageSize,
        Integer pageNum
) {
}
