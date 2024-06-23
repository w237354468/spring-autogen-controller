package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;

public record LcdpControllerQuery(
        @NotEmpty Integer pageSize,
        @NotEmpty Integer pageNum
) {
}
