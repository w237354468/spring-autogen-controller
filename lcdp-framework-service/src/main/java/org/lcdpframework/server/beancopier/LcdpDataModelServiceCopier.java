package org.lcdpframework.server.beancopier;

import org.lcdpframework.dao.dataobject.model.DataModelEntity;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.mapstruct.Mapper;

@Mapper
public interface LcdpDataModelServiceCopier extends IServiceCopier<LcdpDataModelDTO, DataModelEntity> {
}
