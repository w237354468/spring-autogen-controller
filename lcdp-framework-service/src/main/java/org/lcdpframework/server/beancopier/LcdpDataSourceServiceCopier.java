package org.lcdpframework.server.beancopier;

import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdp.framework.dao.dataobject.model.DataSourceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LcdpDataSourceServiceCopier extends IServiceCopier<LcdpDataSourceDTO, DataSourceEntity> {
}
