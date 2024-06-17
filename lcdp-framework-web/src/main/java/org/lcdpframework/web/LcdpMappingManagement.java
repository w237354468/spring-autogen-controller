package org.lcdpframework.web;

import jakarta.validation.Valid;
import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.lcdpframework.server.impl.manager.LcdpMappingService;
import org.lcdpframework.web.copier.LcdpMappingWebCopier;
import org.lcdpframework.web.model.Response;
import org.lcdpframework.web.model.qo.LcdpMappingAdd;
import org.lcdpframework.web.model.qo.LcdpMappingQuery;
import org.lcdpframework.web.model.qo.LcdpMappingUpdate;
import org.lcdpframework.web.model.vo.LcdpMappingResult;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/mappings")
public class LcdpMappingManagement {

    private final LcdpMappingWebCopier mappingWebCopier;
    private final LcdpMappingService mappingService;

    public LcdpMappingManagement(LcdpMappingService mappingService, LcdpMappingWebCopier mappingWebCopier) {
        this.mappingService = mappingService;
        this.mappingWebCopier = mappingWebCopier;
    }

    @PostMapping("/")
    public Response<String> add(@RequestBody @Valid LcdpMappingAdd mappingAdd) {
        String controllerId = mappingService.add(mappingWebCopier.addToDTO(mappingAdd));
        return Response.ok(controllerId);
    }

    @GetMapping("/{id}")
    public Response<LcdpMappingResult> getById(@PathVariable("id") String mappingId) {
        LcdpMappingDTO mappingDTO = mappingService.getById(mappingId);
        return Response.ok(mappingWebCopier.dtoToResult(mappingDTO));
    }

    @GetMapping("/")
    public Response<Page<LcdpMappingResult>> getList(
            @RequestBody @Valid LcdpMappingQuery mappingQuery) {
        Page<LcdpMappingDTO> pageResult = mappingService.getList(
                mappingWebCopier.queryToDTO(mappingQuery));
        return Response.ok(mappingWebCopier.dtoPageToResultPage(pageResult));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delById(@PathVariable("id") String mappingId) {
        mappingService.delete(mappingId);
        return Response.ok();
    }

    @PutMapping("/{id}")
    public Response<Void> update(@PathVariable("id") String mappingId,
                                 @RequestBody @Valid LcdpMappingUpdate update) {
        mappingService.update(mappingId, mappingWebCopier.updateToDTO(update));
        return Response.ok();
    }
}
