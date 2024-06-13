package org.lcdpframework.web.model.vo;

import org.lcdp.framework.dao.dataobject.JoinInfo;
import org.lcdpframework.server.dto.DataModelColumnDTO;

import java.util.List;

public record LcdpDataModelResult(
        String id,
        String dataModelName,
        String describe,
        String dataSourceId,
        List<JoinInfo> joinInfos,
        List<DataModelColumnDTO> dataModelColumns
) {
}
