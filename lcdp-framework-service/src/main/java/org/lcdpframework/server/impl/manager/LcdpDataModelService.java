package org.lcdpframework.server.impl.manager;

import jakarta.transaction.Transactional;
import org.lcdp.framework.dao.dataobject.model.DataModelEntity;
import org.lcdp.framework.dao.repository.DataModelRepository;
import org.lcdpframework.server.beancopier.LcdpDataModelServiceCopier;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LcdpDataModelService {

    private final LcdpDataModelServiceCopier dataModelServiceCopier;
    private final DataModelRepository dataModelRepository;

    public LcdpDataModelService(LcdpDataModelServiceCopier dataModelServiceCopier,DataModelRepository dataModelRepository) {
        this.dataModelServiceCopier = dataModelServiceCopier;
        this.dataModelRepository = dataModelRepository;
    }

    public LcdpDataModelDTO getById(String dataModelId) {
        return dataModelServiceCopier.entityToDTO(dataModelRepository.getReferenceById(dataModelId));
    }

    public Page<LcdpDataModelDTO> getList(LcdpDataModelDTO dataModelDTO) {
        PageRequest page = PageRequest.of(dataModelDTO.getPageNum(), dataModelDTO.getPageSize());
        Page<DataModelEntity> pageResult = dataModelRepository.findAllByDataModelNameLike(dataModelDTO.getDataModelName(), page);
        return pageResult.map(dataModelServiceCopier::entityToDTO);
    }

    @Transactional
    public String add(LcdpDataModelDTO dataModelDTO) {
        DataModelEntity dataModelEntity = dataModelServiceCopier.dtoToEntity(dataModelDTO);
        DataModelEntity saved = dataModelRepository.save(dataModelEntity);
        return saved.getId();
    }

    @Transactional
    public void delete(String dataModelId) {
        dataModelRepository.deleteById(dataModelId);
    }

    @Transactional
    public void update(String dataModelId, LcdpDataModelDTO dataModelDTO) {
        dataModelDTO.setId(dataModelId);
        dataModelRepository.save(dataModelServiceCopier.dtoToEntity(dataModelDTO));
    }
}
