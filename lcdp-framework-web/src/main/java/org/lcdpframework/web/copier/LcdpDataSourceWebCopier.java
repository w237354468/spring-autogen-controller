package org.lcdpframework.web.copier;

import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.web.model.qo.LcdpDataSourceAdd;
import org.lcdpframework.web.model.qo.LcdpDataSourceQuery;
import org.lcdpframework.web.model.qo.LcdpDataModelUpdate;
import org.lcdpframework.web.model.vo.LcdpDataSourceResult;
import org.mapstruct.Mapper;

@Mapper
public interface LcdpDataSourceWebCopier extends IWebCopier<
        LcdpDataSourceAdd, LcdpDataModelUpdate, LcdpDataSourceAdd, LcdpDataSourceQuery, LcdpDataSourceAdd, LcdpDataSourceResult, LcdpDataSourceDTO> {
}
