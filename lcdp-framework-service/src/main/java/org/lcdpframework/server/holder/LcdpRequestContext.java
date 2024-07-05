package org.lcdpframework.server.holder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class LcdpRequestContext {

    private Map<String, Object> requestParamMap;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String mappingUrl;
}
