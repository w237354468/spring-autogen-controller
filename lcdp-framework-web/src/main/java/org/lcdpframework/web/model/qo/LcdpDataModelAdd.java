package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;
import org.lcdpframework.dao.dataobject.JoinInfo;
import org.lcdpframework.server.dto.DataModelColumnsInfoDTO;

import java.util.List;

public record LcdpDataModelAdd(
        @NotEmpty String dataModelName,
        String describe,
        @NotEmpty String dataSourceId,
        @NotEmpty List<JoinInfo> joinInfos,
        @NotEmpty List<DataModelColumnsInfoDTO> dataModelColumns
) {
}
