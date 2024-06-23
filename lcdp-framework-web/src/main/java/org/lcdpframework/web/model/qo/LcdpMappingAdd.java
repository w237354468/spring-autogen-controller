package org.lcdpframework.web.model.qo;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record LcdpMappingAdd(
        @NotEmpty String methodIntent,
        @NotEmpty String httpMethod,
        @NotEmpty String mappingUrl,
        @NotEmpty List<MappingRequestParamAdd> requestParams,
        @NotEmpty List<MappingResponseParamAdd> responseParams,
        String describe
) {
}
