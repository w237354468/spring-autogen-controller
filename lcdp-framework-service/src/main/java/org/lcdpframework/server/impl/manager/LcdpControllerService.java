package org.lcdpframework.server.impl.manager;

import jakarta.transaction.Transactional;
import org.lcdpframework.dao.dataobject.api.DataModelControllerEntity;
import org.lcdpframework.dao.dataobject.api.DataModelControllerMappingEntity;
import org.lcdpframework.dao.repository.DataModelControllerRepository;
import org.lcdpframework.server.beancopier.LcdpDataModelControllerServiceCopier;
import org.lcdpframework.server.dto.LcdpControllerDTO;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LcdpControllerService {

    private final LcdpMappingService mappingService;
    private final LcdpDataModelControllerServiceCopier controllerServiceCopier;
    private final DataModelControllerRepository controllerRepository;

    public LcdpControllerService(LcdpDataModelControllerServiceCopier controllerServiceCopier, DataModelControllerRepository controllerRepository, LcdpMappingService mappingService, ConfigurationPropertiesAutoConfiguration configurationPropertiesAutoConfiguration) {
        this.controllerServiceCopier = controllerServiceCopier;
        this.controllerRepository = controllerRepository;
        this.mappingService = mappingService;
    }

    public Page<LcdpControllerDTO> getList(LcdpControllerDTO controllerDTO) {
        PageRequest page = PageRequest.of(controllerDTO.getPageNum(), controllerDTO.getPageSize());
        Page<DataModelControllerEntity> pageResult = controllerRepository.findAllByControllerNameLike(controllerDTO.getControllerName(), page);
        return pageResult.map(controllerServiceCopier::entityToDTO);
    }

    @Transactional
    public String add(LcdpControllerDTO controllerDTO) {
        DataModelControllerEntity controllerEntity = controllerServiceCopier.dtoToEntity(controllerDTO);
        DataModelControllerEntity saved = controllerRepository.save(controllerEntity);
        return saved.getControllerId();
    }

    @Transactional
    public void delete(String dataModelId) {
        LcdpControllerDTO historyController = getById(dataModelId);
        controllerRepository.deleteById(dataModelId);
        mappingService.deleteByIdIn(historyController.getMappingsEntityList()
                .stream().map(DataModelControllerMappingEntity::getId).toList());
    }

    public LcdpControllerDTO getById(String controllerId) {
        return controllerServiceCopier.entityToDTO(controllerRepository.getReferenceById(controllerId));
    }

    @Transactional
    public void update(String controllerId, LcdpControllerDTO controllerDTO) {
        controllerDTO.setControllerId(controllerId);
        controllerRepository.save(controllerServiceCopier.dtoToEntity(controllerDTO));
    }
}
