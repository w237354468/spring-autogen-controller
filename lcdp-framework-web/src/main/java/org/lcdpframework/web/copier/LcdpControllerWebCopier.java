package org.lcdpframework.web.copier;

import org.lcdpframework.server.dto.LcdpControllerDTO;
import org.lcdpframework.web.model.qo.LcdpControllerAdd;
import org.lcdpframework.web.model.qo.LcdpControllerQuery;
import org.lcdpframework.web.model.qo.LcdpControllerUpdate;
import org.lcdpframework.web.model.vo.LcdpControllerResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LcdpControllerWebCopier extends IWebCopier<
        LcdpControllerAdd, LcdpControllerUpdate, LcdpControllerQuery, LcdpControllerResult, LcdpControllerDTO> {
}
