package org.lcdpframework.server.listener.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.lcdpframework.server.event.impl.LcdpReceiveRequestEvent;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.holder.LcdpRequestContext;
import org.lcdpframework.server.holder.LcdpRequestHolder;
import org.lcdpframework.server.impl.manager.LcdpControllerService;
import org.lcdpframework.server.impl.manager.LcdpMappingService;
import org.lcdpframework.server.log.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.lcdpframework.server.log.Log.LOGGER_TYPE.SYSTEM;

/**
 * cache request inside thread
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LcdpReceiveRequestPriorityListener implements ApplicationListener<LcdpReceiveRequestEvent> {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final LcdpMappingService mappingService;
    private final LcdpControllerService controllerService;

    public LcdpReceiveRequestPriorityListener(LcdpMappingService mappingService, LcdpControllerService controllerService) {
        this.mappingService = mappingService;
        this.controllerService = controllerService;
    }

    @Override
    public void onApplicationEvent(LcdpReceiveRequestEvent event) {

        Object[] source = (Object[]) event.getSource();
        HttpServletRequest request = (HttpServletRequest) source[0];
        HttpServletResponse response = (HttpServletResponse) source[1];
        String pid = (String) source[2];
        Map<String, Object> body = (Map<String, Object>) source[3];
        Map<String, Object> params = (Map<String, Object>) source[4];

        String resourceUrl = getResourceUrl(request);
        Log.info(SYSTEM, "receive new request {} from : [{}]", request.getMethod(), resourceUrl);

        LcdpMappingDTO mappingDTO = mappingService.findByMappingUrlEquals(resourceUrl);
        if (Objects.isNull(mappingDTO)) {
            throw new RuntimeException("can not find mapping entity which associate with specific url");
        }

        LcdpDataModelDTO dataModelDTO = controllerService.findDataModelByControllerId(mappingDTO.getControllerId());
        if (Objects.isNull(dataModelDTO)) {
            throw new RuntimeException("can not find specific data model");
        }

        Map<String, Object> requestMap = getRequestParamMap(body, params, pid);

        LcdpGlobalParamHolder.setDataModel(dataModelDTO);
        LcdpGlobalParamHolder.setMapping(mappingDTO);

        //  cache request inside thread
        LcdpRequestHolder.setRequestContext(
                LcdpRequestContext.builder()
                        .requestParamMap(requestMap)
                        .mappingUrl(resourceUrl)
                        .request(request)
                        .response(response)
                        .build());
    }

    private String getResourceUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.replace(contextPath, "");
    }

    private Map<String, Object> getRequestParamMap(Map<String, Object> body, Map<String, Object> params, String pid) {
        Map<String, Object> requestParamMap = new LinkedHashMap<>();
        Optional.ofNullable(body).ifPresent(requestParamMap::putAll);
        Optional.ofNullable(params).ifPresent(requestParamMap::putAll);
        Optional.ofNullable(pid).ifPresent(e -> requestParamMap.put("pid", pid));
        return requestParamMap;
    }
}
