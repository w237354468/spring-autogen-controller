package org.lcdpframework.starter.register;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lcdpframework.dao.dataobject.api.DataModelControllerEntity;
import org.lcdpframework.dao.dataobject.api.DataModelControllerMappingEntity;
import org.lcdpframework.dao.repository.DataModelControllerRepository;
import org.lcdpframework.server.log.Log;
import org.lcdpframework.web.DynamicRequestHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;

import static org.lcdpframework.server.log.Log.LOGGER_TYPE.SYSTEM;

@Configuration
public class UrlMappingAutoRegistryListener {

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private DynamicRequestHandler dynamicRequestHandler;

    @PostConstruct
    public void registerDynamicUrl() {

        var handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        var controllerRepository = applicationContext.getBean(DataModelControllerRepository.class);

        // load all mappings created by lcdp
        List<DataModelControllerEntity> lcdpControllers = controllerRepository.findAll();
        for (DataModelControllerEntity controller : lcdpControllers) {

            List<DataModelControllerMappingEntity> mappingsList = controller.getMappingsList();

            for (DataModelControllerMappingEntity mapping : mappingsList) {

                RequestMappingInfo requestMappingInfo = buildRequestMappingInfo(mapping);
                try {
                    handlerMapping.registerMapping(requestMappingInfo, dynamicRequestHandler,
                            dynamicRequestHandler.getClass().getMethod(
                                    mapping.getHttpMethod().toLowerCase(),
                                    HttpServletRequest.class, HttpServletResponse.class,
                                    String.class, Map.class, Map.class));

                    Log.debug(SYSTEM, "register dynamic [{}] requestMapping [{}]",
                            mapping.getHttpMethod(), mapping.getMappingUrl());

                } catch (NoSuchMethodException e) {
                    Log.debug(SYSTEM, "register dynamic url [{}] failed: {}, please check mapping config",
                            mapping.getMappingUrl(), e.getMessage());

                    throw new RuntimeException(e);
                }
            }
        }
    }

    private RequestMappingInfo buildRequestMappingInfo(DataModelControllerMappingEntity mapping) {
        String mappingUrl = mapping.getMappingUrl();
        String httpMethod = mapping.getHttpMethod();

        return RequestMappingInfo
                .paths(mappingUrl)
                .methods(getHttpMethod(httpMethod))
                .build();
    }

    private RequestMethod getHttpMethod(String httpMethod) {
        return RequestMethod.resolve(httpMethod.toUpperCase());
    }
}