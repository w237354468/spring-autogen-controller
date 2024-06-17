package org.lcdpframework.server.beancopier;

import org.lcdpframework.dao.dataobject.api.DataModelControllerMappingEntity;
import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.mapstruct.Mapper;

@Mapper
public interface LcdpDataModelMappingServiceCopier extends IServiceCopier<
        LcdpMappingDTO, DataModelControllerMappingEntity> {
}
