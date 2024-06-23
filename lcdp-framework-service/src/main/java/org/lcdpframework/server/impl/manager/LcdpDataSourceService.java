package org.lcdpframework.server.impl.manager;

import jakarta.transaction.Transactional;
import org.lcdpframework.dao.dataobject.model.DataSourceEntity;
import org.lcdpframework.dao.repository.DataSourceRepository;
import org.lcdpframework.server.beancopier.LcdpDataSourceServiceCopier;
import org.lcdpframework.server.dto.DataModelColumnsInfoDTO;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.dto.TableDetailInfo;
import org.lcdpframework.server.event.impl.DataSourceChangeEvent;
import org.lcdpframework.server.function.TableColumnResolver;
import org.lcdpframework.server.function.TableStructureResolver;
import org.lcdpframework.server.log.Log;
import org.lcdpframework.server.util.JacksonUtil;
import org.lcdpframework.server.util.LcdpEventUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.lcdpframework.server.log.Log.LOGGER_TYPE.SYSTEM;

@Service
public class LcdpDataSourceService {

    private final DataSourceRepository dataSourceRepository;
    private final LcdpDataSourceServiceCopier dataSourceServiceCopier;

    public LcdpDataSourceService(DataSourceRepository dataSourceRepository, LcdpDataSourceServiceCopier dataSourceServiceCopier) {
        this.dataSourceRepository = dataSourceRepository;
        this.dataSourceServiceCopier = dataSourceServiceCopier;
    }

    public Page<LcdpDataSourceDTO> getList(LcdpDataSourceDTO dataSourceQuery) {
        PageRequest page = PageRequest.of(dataSourceQuery.getPageNum() - 1, dataSourceQuery.getPageSize());
        Page<DataSourceEntity> pageResult = dataSourceRepository.findAllByDataSourceNameLike(
                '%' + dataSourceQuery.getDataSourceName() + '%', page);
        return pageResult.map(dataSourceServiceCopier::entityToDTO);
    }

    @Transactional
    public String add(LcdpDataSourceDTO dataSourceDTO) {
        DataSourceEntity dataSourceEntity = dataSourceServiceCopier.dtoToEntity(dataSourceDTO);
        DataSourceEntity saved = dataSourceRepository.save(dataSourceEntity);
        LcdpEventUtil.publishEvent(DataSourceChangeEvent.of(null, dataSourceDTO));

        Log.info(SYSTEM, "saved new data source object : {} ", JacksonUtil.toJson(saved));
        return saved.getDataSourceId();
    }

    @Transactional
    public void delete(String dataSourceId) {
        LcdpDataSourceDTO previousDataSource = getById(dataSourceId);
        dataSourceRepository.deleteById(dataSourceId);
        LcdpEventUtil.publishEvent(DataSourceChangeEvent.of(previousDataSource, null));
    }

    public LcdpDataSourceDTO getById(String dataSourceId) {
        return dataSourceServiceCopier.entityToDTO(dataSourceRepository.getReferenceById(dataSourceId));
    }

    @Transactional
    public void update(String dataSourceId, LcdpDataSourceDTO newDataSource) {
        LcdpDataSourceDTO previousDataSource = getById(dataSourceId);
        newDataSource.setDataSourceId(dataSourceId);
        dataSourceRepository.save(dataSourceServiceCopier.dtoToEntity(newDataSource));
        LcdpEventUtil.publishEvent(DataSourceChangeEvent.of(previousDataSource, newDataSource));
    }

    @Transactional
    public String testConnect(String dataSourceId) {
        LcdpDataSourceDTO dataSourceDTO = getById(dataSourceId);
        String account = dataSourceDTO.getAccount();
        String password = dataSourceDTO.getPassword();
        String url = dataSourceDTO.getDataSourceUrl();
        try (Connection conn = DriverManager.getConnection(url, account, password)) {
            return conn != null ? "success" : "failed";
        } catch (SQLException e) {
            Log.error(SYSTEM, "test connect failed: {}", e.getMessage(), e);
            return "failed: " + e.getMessage();
        }
    }

    public List<TableDetailInfo> connect(String dataSourceId) {
        Optional<DataSourceEntity> dataSourceEntity = dataSourceRepository.findById(dataSourceId);
        LcdpDataSourceDTO dataSourceDTO = dataSourceServiceCopier.entityToDTO(dataSourceEntity.orElseThrow());
        return new TableStructureResolver().apply(dataSourceDTO);
    }

    public List<DataModelColumnsInfoDTO> queryColumns(String dataSourceId, List<String> tableNames) {
        LcdpDataSourceDTO dataSourceDTO = getById(dataSourceId);
        return new TableColumnResolver(tableNames).apply(dataSourceDTO);
    }
}
