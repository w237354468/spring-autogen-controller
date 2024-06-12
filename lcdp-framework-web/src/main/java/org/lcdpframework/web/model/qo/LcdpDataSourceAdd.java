package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;

public record LcdpDataSourceAdd(
        @NotEmpty String dataSourceName,
        @NotEmpty String databaseType,
        @NotEmpty String dataSourceUrl,
        @NotEmpty String account,
        @NotEmpty String password
) {
}
