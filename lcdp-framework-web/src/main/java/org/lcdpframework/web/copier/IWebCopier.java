package org.lcdpframework.web.copier;

import org.springframework.data.domain.Page;

public interface IWebCopier<Add, Update, Query, Result, DTO> {

    DTO addToDTO(Add add);

    DTO updateToDTO(Update del);

    DTO queryToDTO(Query query);

    Result dtoToResult(DTO dto);

    Page<Result> dtoPageToResultPage(Page<DTO> dtoPage);

}
