package org.lcdpframework.web.model.vo;

import org.lcdpframework.web.model.qo.LcdpMappingAdd;

import java.time.LocalDateTime;
import java.util.List;

public record LcdpControllerResult(
        String controllerName,
        String controllerType,
        String dataModelId,
        String url,
        String describe,
        String publishStatus,
        LocalDateTime publishTime,
        String controllerStatus,
        List<LcdpMappingAdd> mappingsList
) {
}
