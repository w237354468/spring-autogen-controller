package org.lcdpframework.web.model.vo;

import org.lcdp.framework.dao.dataobject.model.DataModelEntity;

import java.util.List;

public record LcdpDataSourceResult(
        String dataSourceId,
        List<DataModelEntity> dataModelId,
        String dataSourceName,
        String databaseType,
        String dataSourceUrl,
        String account,
        String password
) {
}
