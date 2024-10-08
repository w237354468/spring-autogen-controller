package org.lcdpframework.starter;

import com.zaxxer.hikari.HikariDataSource;
import org.lcdpframework.server.datasource.DynamicDataSource;
import org.lcdpframework.server.datasource.DynamicDataSourceHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

import static org.lcdpframework.server.constants.BaseConstants.DEFAULT_DATA_SOURCE;

/**
 * create a new dynamic data source which contains several datasources including main default from properties and other datasources from lcdp database
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
public class DynamicDataSourceAutoConfigure {

    @Bean
    @Primary
    public DynamicDataSource dynamicSource(DataSourceProperties dataSourceProperties) {

        HikariDataSource mainDataSource = initMainDataSource(dataSourceProperties);

        DynamicDataSourceHolder.putMainDataSource(mainDataSource);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        dynamicDataSource.setTargetDataSources(Map.of(DEFAULT_DATA_SOURCE, mainDataSource));
        dynamicDataSource.setDefaultTargetDataSource(mainDataSource);
        dynamicDataSource.afterPropertiesSet();

        return dynamicDataSource;
    }

    private HikariDataSource initMainDataSource(DataSourceProperties dataSourceProperties) {
        return DynamicDataSourceHolder.getDataSourceByDataModelDTO(dataSourceProperties);
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean(TransactionTemplate.class)
    public TransactionTemplate transactionTemplate(DataSourceTransactionManager manager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(manager);
        return transactionTemplate;
    }
}