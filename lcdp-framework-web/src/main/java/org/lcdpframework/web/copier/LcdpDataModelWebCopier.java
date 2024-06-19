package org.lcdpframework.web.copier;

import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.web.model.qo.LcdpDataModelAdd;
import org.lcdpframework.web.model.qo.LcdpDataModelQuery;
import org.lcdpframework.web.model.qo.LcdpDataModelUpdate;
import org.lcdpframework.web.model.vo.LcdpDataModelResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LcdpDataModelWebCopier extends IWebCopier
        <LcdpDataModelAdd, LcdpDataModelUpdate, LcdpDataModelQuery, LcdpDataModelResult, LcdpDataModelDTO> {
}
