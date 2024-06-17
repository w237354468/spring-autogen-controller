package org.lcdpframework.server.impl.manager;

import jakarta.transaction.Transactional;
import org.lcdpframework.dao.dataobject.api.DataModelControllerMappingEntity;
import org.lcdpframework.dao.repository.DataModelControllerMappingRepository;
import org.lcdpframework.server.beancopier.LcdpDataModelMappingServiceCopier;
import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LcdpMappingService {
    private final LcdpDataModelMappingServiceCopier mappingServiceCopier;
    private final DataModelControllerMappingRepository mappingRepository;

    public LcdpMappingService(LcdpDataModelMappingServiceCopier mappingServiceCopier, DataModelControllerMappingRepository mappingRepository) {
        this.mappingServiceCopier = mappingServiceCopier;
        this.mappingRepository = mappingRepository;
    }

    public LcdpMappingDTO getById(String mappingId) {
        return mappingServiceCopier.entityToDTO(mappingRepository.getReferenceById(mappingId));
    }

    public Page<LcdpMappingDTO> getList(LcdpMappingDTO controllerDTO) {
        PageRequest page = PageRequest.of(controllerDTO.getPageNum(), controllerDTO.getPageSize());
        Page<DataModelControllerMappingEntity> pageResult = mappingRepository.findAll(page);
        return pageResult.map(mappingServiceCopier::entityToDTO);
    }

    @Transactional
    public String add(LcdpMappingDTO mappingDTO) {
        DataModelControllerMappingEntity mappingEntity = mappingServiceCopier.dtoToEntity(mappingDTO);
        DataModelControllerMappingEntity saved = mappingRepository.save(mappingEntity);
        return saved.getId();
    }

    @Transactional
    public void delete(String dataModelId) {
        mappingRepository.deleteById(dataModelId);
    }

    @Transactional
    public void deleteByIdIn(List<String> mappingIds) {
        mappingRepository.deleteAllById(mappingIds);
    }

    @Transactional
    public void update(String mappingId, LcdpMappingDTO mappingDTO) {
        mappingDTO.setId(mappingId);
        mappingRepository.save(mappingServiceCopier.dtoToEntity(mappingDTO));
    }
}
