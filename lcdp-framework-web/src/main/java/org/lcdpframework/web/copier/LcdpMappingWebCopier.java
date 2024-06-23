package org.lcdpframework.web.copier;

import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.lcdpframework.web.model.qo.LcdpMappingAdd;
import org.lcdpframework.web.model.qo.LcdpMappingQuery;
import org.lcdpframework.web.model.vo.LcdpMappingResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LcdpMappingWebCopier extends IWebCopier<
        LcdpMappingAdd, LcdpMappingQuery, LcdpMappingResult, LcdpMappingDTO> {
}
