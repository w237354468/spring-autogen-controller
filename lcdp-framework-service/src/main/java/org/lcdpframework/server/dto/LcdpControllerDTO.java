package org.lcdpframework.server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lcdpframework.dao.dataobject.api.DataModelControllerMappingEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LcdpControllerDTO extends PageQuery implements Serializable {
    private String controllerId;
    private String controllerName;
    private String controllerType;
    private String dataModelId;
    private LcdpDataModelDTO dataModel;
    private String url;
    private String describe;
    private String publishStatus;
    private LocalDateTime publishTime;
    private String controllerStatus;
    private List<DataModelControllerMappingEntity> mappingsList = new ArrayList<>();
}
