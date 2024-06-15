package org.lcdpframework.server.beancopier;

import org.lcdpframework.dao.dataobject.model.DataSourceEntity;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LcdpDataSourceServiceCopier extends IServiceCopier<LcdpDataSourceDTO, DataSourceEntity> {
}
