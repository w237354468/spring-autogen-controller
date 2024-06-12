package org.lcdpframework.web.model.qo;

public record LcdpDataModelUpdate(
         String dataSourceName,
         String databaseType,
         String dataSourceUrl,
         String account,
         String password
) {
}
