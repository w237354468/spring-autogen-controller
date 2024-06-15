package org.lcdpframework.server.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.log.LcdpLog;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

import static org.lcdpframework.server.log.LcdpLog.LOGGER_TYPE.SYSTEM;

public class DynamicDataSourceHolder implements EnvironmentAware, ApplicationContextAware {

    private static final LinkedHashMap<Object, Object> targetDataSource = new LinkedHashMap<>();
    private static final String DEFAULT_VALIDATION_QUERY = "select 1";

    private static ApplicationContext applicationContext = null;
    private static Environment environment = null;

    public static void putMainDataSource(DataSource dataSource) {
        targetDataSource.put("default", dataSource);
    }

    public static void putDataSource(LcdpDataSourceDTO dataSourceDTO) {
        String dataSourceKey = dataSourceDTO.getDataSourceId();
        targetDataSource.put(dataSourceKey, getDataSourceByDataModelDTO(dataSourceDTO));
        LcdpLog.printInfo(SYSTEM, "cache new datasource : {}", dataSourceKey);
    }

    public static HikariDataSource getDataSourceByDataModelDTO(LcdpDataSourceDTO dataSource) {

        HikariConfig prop = new HikariConfig();
        prop.setPassword(dataSource.getPassword());
        prop.setUsername(dataSource.getAccount());
        prop.setJdbcUrl(dataSource.getDataSourceUrl());
        prop.setConnectionTestQuery(DEFAULT_VALIDATION_QUERY);
        prop.setDataSourceClassName(environment.getProperty("spring.datasource.driver-class-nam"));
        prop.setPoolName(dataSource.getDataSourceName());
        return getDataSourceByProperties(prop);
    }

    private static HikariDataSource getDataSourceByProperties(HikariConfig prop) {
        return new HikariDataSource(prop);
    }

    public static void removeDataSource(String dataSourceKey) {
        targetDataSource.remove(dataSourceKey);
    }

    public static void clearDataSource() {
        Object aDefault = targetDataSource.get("default");
        targetDataSource.clear();
        targetDataSource.put("default", aDefault);
    }

    public static String getDbKey() {
        String param = LcdpGlobalParamHolder.getCurrentDatasource();
        if (StringUtils.hasText(param)) {
            return "default";
        } else {
            return param;
        }
    }

    public static void resetDbKeys() {
        LcdpGlobalParamHolder.setCurrentDatasource("default");
    }

    public static void changeDataSource(LcdpDataSourceDTO dataSource) {
        try {
            LcdpDynamicDataSource dynamicDataSource = applicationContext.getBean(LcdpDynamicDataSource.class);
            String dataSourceId = dataSource.getDataSourceId();

            if (!targetDataSource.containsKey(dataSourceId)) {
                targetDataSource.put(dataSourceId, getDataSourceByDataModelDTO(dataSource));
                dynamicDataSource.setTargetDataSources(targetDataSource);
                dynamicDataSource.afterPropertiesSet();
                LcdpLog.printInfo(SYSTEM, "register new datasource: {} ({})",
                        dataSource.getDataSourceName(), dataSourceId);
            } else LcdpLog.printInfo(SYSTEM, "datasource had loaded:{} ({})",
                    dataSource.getDataSourceName(), dataSourceId);
            switchDbKeys(dataSourceId);
            HikariDataSource currentDS = (HikariDataSource) targetDataSource.get(dataSourceId);
            LcdpLog.printInfo(SYSTEM, "switch datasource to: {} ({})",
                    currentDS.getPoolName(), dataSourceId);

        } catch (BeansException e) {
            LcdpLog.printError(SYSTEM, "fail to resolve dynamic data source bean!");
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
        prop.setDataSourceClassName(dataSourceProperties.getDriverClassName());
        prop.setPoolName(dataSourceProperties.getName());
        return getDataSourceByProperties(prop);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}