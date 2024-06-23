package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record LcdpControllerAdd(
        @NotEmpty String controllerName,
        @NotEmpty String controllerType,
        @NotEmpty String dataModelId,
        @NotEmpty String url,
        @NotEmpty String describe,
        @NotEmpty String publishStatus,
        @NotEmpty LocalDateTime publishTime,
        @NotEmpty String controllerStatus,
        List<LcdpMappingAdd> mappingsList
) {
}
