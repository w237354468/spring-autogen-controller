package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;

public record MappingResponseParamAdd(
        @NotEmpty String columnId,
        @NotEmpty String defineType,
        @NotEmpty String paramName
) {
}
