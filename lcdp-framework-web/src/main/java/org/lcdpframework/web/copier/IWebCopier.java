package org.lcdpframework.web.copier;

import org.springframework.data.domain.Page;

public interface IWebCopier<Add, Update, Delete, Query, Patch, Result, DTO> {

    DTO addToDTO(Add add);

    DTO updateToDTO(Update del);

    DTO deleteToDTO(Delete del);

    DTO queryToDTO(Query query);

    DTO PatchToDTO(Patch patch);

    Result dtoToResult(DTO dto);

    Page<Result> dtoPageToResultPage(Page<DTO> dtoPage);

}
