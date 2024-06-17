package org.lcdpframework.web.copier;

import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.lcdpframework.web.model.qo.LcdpMappingAdd;
import org.lcdpframework.web.model.qo.LcdpMappingQuery;
import org.lcdpframework.web.model.qo.LcdpMappingUpdate;
import org.lcdpframework.web.model.vo.LcdpMappingResult;
import org.mapstruct.Mapper;

@Mapper
public interface LcdpMappingWebCopier extends IWebCopier<
        LcdpMappingAdd, LcdpMappingUpdate, LcdpMappingQuery, LcdpMappingResult, LcdpMappingDTO> {
}
