package org.lcdpframework.web;

import jakarta.validation.Valid;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.impl.manager.LcdpDataModelService;
import org.lcdpframework.web.copier.LcdpDataModelWebCopier;
import org.lcdpframework.web.model.Response;
import org.lcdpframework.web.model.qo.LcdpDataModelAdd;
import org.lcdpframework.web.model.qo.LcdpDataModelQuery;
import org.lcdpframework.web.model.qo.LcdpDataModelUpdate;
import org.lcdpframework.web.model.vo.LcdpDataModelResult;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/datamodels")
public class LcdpDataModelController {

    private final LcdpDataModelWebCopier dataModelWebCopier;
    private final LcdpDataModelService dataModelService;

    public LcdpDataModelController(LcdpDataModelService dataModelService, LcdpDataModelWebCopier dataModelWebCopier) {
        this.dataModelService = dataModelService;
        this.dataModelWebCopier = dataModelWebCopier;
    }

    @PostMapping("/")
    public Response<String> add(@RequestBody @Valid LcdpDataModelAdd dataModelAdd) {
        String dataModelId = dataModelService.add(dataModelWebCopier.addToDTO(dataModelAdd));
        return Response.ok(dataModelId);
    }

    @GetMapping("/{id}")
    public Response<LcdpDataModelResult> getById(@PathVariable("id") String dataModelId) {
        LcdpDataModelDTO dataModelDTO = dataModelService.getById(dataModelId);
        return Response.ok(dataModelWebCopier.dtoToResult(dataModelDTO));
    }

    @GetMapping("/")
    public Response<Page<LcdpDataModelResult>> getList(
            @RequestBody @Valid LcdpDataModelQuery dataModelQuery) {
        Page<LcdpDataModelDTO> pageResult = dataModelService.getList(
                dataModelWebCopier.queryToDTO(dataModelQuery));
        return Response.ok(dataModelWebCopier.dtoPageToResultPage(pageResult));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delById(@PathVariable("id") String dataModelId) {
        dataModelService.delete(dataModelId);
        return Response.ok();
    }

    @PutMapping("/{id}")
    public Response<Void> update(@PathVariable("id") String dataModelId,
                                 @RequestBody @Valid LcdpDataModelUpdate update) {
        dataModelService.update(dataModelId, dataModelWebCopier.updateToDTO(update));
        return Response.ok();
    }
}
