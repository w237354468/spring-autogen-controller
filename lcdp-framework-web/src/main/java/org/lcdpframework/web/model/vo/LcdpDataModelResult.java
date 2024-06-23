package org.lcdpframework.web.model.vo;

import org.lcdpframework.dao.dataobject.JoinInfo;
import org.lcdpframework.server.dto.DataModelColumnsInfoDTO;

import java.util.List;

public record LcdpDataModelResult(
        String id,
        String dataModelName,
        String describe,
        String dataSourceId,
        LcdpDataSourceResult dataSource,
        List<JoinInfo> joinInfos,
        List<DataModelColumnsInfoDTO> dataModelColumns
) {
}
