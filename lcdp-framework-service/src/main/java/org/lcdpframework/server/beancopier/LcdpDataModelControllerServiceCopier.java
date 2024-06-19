package org.lcdpframework.server.beancopier;

import org.lcdpframework.dao.dataobject.api.DataModelControllerEntity;
import org.lcdpframework.server.dto.LcdpControllerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LcdpDataModelControllerServiceCopier extends IServiceCopier<LcdpControllerDTO, DataModelControllerEntity> {
}
