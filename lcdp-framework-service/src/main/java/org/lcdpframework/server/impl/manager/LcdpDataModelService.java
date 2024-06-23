package org.lcdpframework.server.impl.manager;

import jakarta.transaction.Transactional;
import org.lcdpframework.dao.dataobject.model.DataModelEntity;
import org.lcdpframework.dao.repository.DataModelRepository;
import org.lcdpframework.server.beancopier.LcdpDataModelServiceCopier;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LcdpDataModelService {

    private final LcdpDataSourceService dataSourceService;
    private final LcdpDataModelServiceCopier dataModelServiceCopier;
    private final DataModelRepository dataModelRepository;

    public LcdpDataModelService(LcdpDataModelServiceCopier dataModelServiceCopier, DataModelRepository dataModelRepository, LcdpDataSourceService dataSourceService) {
        this.dataModelServiceCopier = dataModelServiceCopier;
        this.dataModelRepository = dataModelRepository;
        this.dataSourceService = dataSourceService;
    }

    public LcdpDataModelDTO getById(String dataModelId) {
        return dataModelServiceCopier.entityToDTO(dataModelRepository.getReferenceById(dataModelId));
    }

    public Page<LcdpDataModelDTO> getList(LcdpDataModelDTO dataModelDTO) {
        PageRequest page = PageRequest.of(dataModelDTO.getPageNum() - 1, dataModelDTO.getPageSize());
        Page<DataModelEntity> pageResult = dataModelRepository.findAllByDataModelNameLikeIgnoreCase(
                '%' + dataModelDTO.getDataModelName() + '%', page);
        return pageResult.map(dataModelServiceCopier::entityToDTO);
    }

    @Transactional
    public String add(LcdpDataModelDTO dataModelDTO) {
        LcdpDataSourceDTO dataSourceDTO = dataSourceService.getById(dataModelDTO.getDataSourceId());
        dataModelDTO.setDataSource(dataSourceDTO);
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
        LcdpDataSourceDTO dataSourceDTO = dataSourceService.getById(dataModelDTO.getDataSourceId());
        dataModelDTO.setDataSource(dataSourceDTO);
        dataModelRepository.save(dataModelServiceCopier.dtoToEntity(dataModelDTO));
    }
}
