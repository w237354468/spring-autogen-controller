package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;

public record LcdpMappingQuery(
        @NotEmpty String mappingName,
        @NotEmpty Integer pageSize,
        @NotEmpty Integer pageNum
) {
}
