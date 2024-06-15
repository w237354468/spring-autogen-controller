package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;
import org.lcdpframework.dao.dataobject.JoinInfo;
import org.lcdpframework.server.dto.DataModelColumnDTO;

import java.util.List;

public record LcdpDataModelAdd(
        @NotEmpty String dataModelName,
        @NotEmpty String describe,
        @NotEmpty String dataSourceId,
        @NotEmpty List<JoinInfo> joinInfos,
        @NotEmpty List<DataModelColumnDTO> dataModelColumns
) {
}
