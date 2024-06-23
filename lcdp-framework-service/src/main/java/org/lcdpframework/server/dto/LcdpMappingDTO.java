package org.lcdpframework.server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lcdpframework.dao.dataobject.api.MappingRequestParamEntity;
import org.lcdpframework.dao.dataobject.api.MappingResponseParamEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LcdpMappingDTO extends PageQuery implements Serializable {
    private String id;
    private String name;
    private String controllerId;
    private String methodIntent;
    private String httpMethod;
    private String mappingUrl;
    private List<MappingRequestParamEntity> requestParams = new ArrayList<>();
    private List<MappingResponseParamEntity> responsesParams = new ArrayList<>();
    private String describe;
}
