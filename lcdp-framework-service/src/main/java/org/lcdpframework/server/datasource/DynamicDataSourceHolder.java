package org.lcdpframework.server.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.log.Log;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;

import static org.lcdpframework.server.constants.BaseConstants.DEFAULT_DATA_SOURCE;
import static org.lcdpframework.server.log.Log.LOGGER_TYPE.SYSTEM;

@Component
public class DynamicDataSourceHolder implements ApplicationContextAware {

    private static final LinkedHashMap<Object, Object> targetDataSource = new LinkedHashMap<>();
    private static final String DEFAULT_VALIDATION_QUERY = "select 1";

    private static ApplicationContext applicationContext = null;

    public static void putMainDataSource(HikariDataSource dataSource) {
        targetDataSource.put(DEFAULT_DATA_SOURCE, dataSource);
    }

    public static void putDataSource(LcdpDataSourceDTO dataSourceDTO) {
        String dataSourceKey = dataSourceDTO.getDataSourceId();
        targetDataSource.put(dataSourceKey, getDataSourceByDataModelDTO(dataSourceDTO));
        Log.info(SYSTEM, "cache new datasource : {}", dataSourceKey);
    }

    public static HikariDataSource getDataSourceByDataModelDTO(LcdpDataSourceDTO dataSource) {

        HikariConfig prop = new HikariConfig();
        prop.setPassword(dataSource.getPassword());
        prop.setUsername(dataSource.getAccount());
        prop.setJdbcUrl(dataSource.getDataSourceUrl());
        prop.setConnectionTestQuery(DEFAULT_VALIDATION_QUERY);
        String driverClassName = applicationContext.getEnvironment().getProperty("spring.datasource.driver-class-name");
        prop.setDriverClassName(driverClassName);
        prop.setPoolName(dataSource.getDataSourceName());
        return new HikariDataSource(prop);
    }

    public static void removeDataSource(String dataSourceKey) {
        targetDataSource.remove(dataSourceKey);
    }

    public static void clearDataSource() {
        Object aDefault = targetDataSource.get(DEFAULT_DATA_SOURCE);
        targetDataSource.clear();
        targetDataSource.put(DEFAULT_DATA_SOURCE, aDefault);
    }

    public static String getDbKey() {
        String param = LcdpGlobalParamHolder.getCurrentDatasource();
        if (StringUtils.hasText(param)) {
            return DEFAULT_DATA_SOURCE;
        } else {
            return param;
        }
    }

    public static void resetDbKeys() {
        LcdpGlobalParamHolder.setCurrentDatasource(DEFAULT_DATA_SOURCE);
    }

    public static void changeDataSource(LcdpDataSourceDTO dataSource) {
        try {
            String dataSourceId = dataSource.getDataSourceId();

            if (!targetDataSource.containsKey(dataSourceId)) {
                LcdpDynamicDataSource dynamicDataSource = applicationContext.getBean(LcdpDynamicDataSource.class);

                targetDataSource.put(dataSourceId, getDataSourceByDataModelDTO(dataSource));

                dynamicDataSource.setTargetDataSources(targetDataSource);
                dynamicDataSource.afterPropertiesSet();

                Log.info(SYSTEM, "register new datasource: {} ({})", dataSource.getDataSourceName(), dataSourceId);
            } else {
                Log.info(SYSTEM, "datasource had loaded:{} ({})", dataSource.getDataSourceName(), dataSourceId);
            }

            switchDbKeys(dataSourceId);
            Log.info(SYSTEM, "switch datasource to: {}", dataSourceId);

        } catch (BeansException e) {
            Log.error(SYSTEM, "fail to resolve dynamic data source bean!");
        }
    }

    public static void switchDbKeys(String dbKey) {
        LcdpGlobalParamHolder.setCurrentDatasource(dbKey);
    }

    public static HikariDataSource getDataSourceByDataModelDTO(DataSourceProperties dataSourceProperties) {
        HikariConfig prop = new HikariConfig();
        prop.setPassword(dataSourceProperties.getPassword());
        prop.setUsername(dataSourceProperties.getUsername());
        prop.setJdbcUrl(dataSourceProperties.getUrl());
        prop.setConnectionTestQuery(DEFAULT_VALIDATION_QUERY);
        prop.setDriverClassName(dataSourceProperties.getDriverClassName());
        prop.setPoolName(dataSourceProperties.getName());
        return new HikariDataSource(prop);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}