package org.lcdpframework.server.beancopier;

import java.util.List;

public interface IServiceCopier<Source, Target> {

    Target dtoToEntity(Source dto);

    Source entityToDTO(Target entity);

    List<Target> dtoToEntityList(List<Source> dtoList);

    List<Source> entityToDtoList(List<Target> entityList);

}