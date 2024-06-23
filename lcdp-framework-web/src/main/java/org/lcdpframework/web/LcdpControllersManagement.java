package org.lcdpframework.web;

import jakarta.validation.Valid;
import org.lcdpframework.server.dto.LcdpControllerDTO;
import org.lcdpframework.server.impl.manager.LcdpControllerService;
import org.lcdpframework.web.copier.LcdpControllerWebCopier;
import org.lcdpframework.web.model.Response;
import org.lcdpframework.web.model.qo.LcdpControllerAdd;
import org.lcdpframework.web.model.qo.LcdpControllerQuery;
import org.lcdpframework.web.model.vo.LcdpControllerResult;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/controllers")
public class LcdpControllersManagement {

    private final LcdpControllerWebCopier controllerWebCopier;
    private final LcdpControllerService controllerService;

    public LcdpControllersManagement(LcdpControllerService controllerService, LcdpControllerWebCopier controllerWebCopier) {
        this.controllerService = controllerService;
        this.controllerWebCopier = controllerWebCopier;
    }

    @PostMapping
    public Response<String> add(@RequestBody @Valid LcdpControllerAdd controllerAdd) {
        String controllerId = controllerService.add(controllerWebCopier.addToDTO(controllerAdd));
        return Response.ok(controllerId);
    }

    @GetMapping("/{id}")
    public Response<LcdpControllerResult> getById(@PathVariable("id") String controllerId) {
        LcdpControllerDTO controllerDTO = controllerService.getById(controllerId);
        return Response.ok(controllerWebCopier.dtoToResult(controllerDTO));
    }

    @GetMapping
    public Response<Page<LcdpControllerResult>> getList(
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum) {
        LcdpControllerQuery controllerQuery = new LcdpControllerQuery(pageSize, pageNum);
        Page<LcdpControllerDTO> pageResult = controllerService.getList(
                controllerWebCopier.queryToDTO(controllerQuery));
        return Response.ok(controllerWebCopier.dtoPageToResultPage(pageResult));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delById(@PathVariable("id") String controllerId) {
        controllerService.delete(controllerId);
        return Response.ok();
    }

    @PutMapping("/{id}")
    public Response<Void> update(@PathVariable("id") String dataModelId,
                                 @RequestBody @Valid LcdpControllerAdd update) {
        controllerService.update(dataModelId, controllerWebCopier.addToDTO(update));
        return Response.ok();
    }
}
