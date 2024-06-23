package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;

public record MappingRequestParamAdd(
        @NotEmpty String columnId,
        @NotEmpty String defineType,
        @NotEmpty String queryType,
        @NotEmpty String paramName,
        @NotEmpty String paramType
) {
}
