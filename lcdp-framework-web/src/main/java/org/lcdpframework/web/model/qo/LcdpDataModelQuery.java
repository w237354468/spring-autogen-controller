package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;

public record LcdpDataModelQuery(
        @NotEmpty String dataModelName,
        @NotEmpty Integer pageSize,
        @NotEmpty Integer pageNum
) {
}
