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

import javax.sql.DataSource;
import java.util.Map;

/**
 * create a new dynamic data source which contains several datasources including main default from properties and other datasources from lcdp database
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
public class DynamicDataSourceAutoConfigure {

    @Bean
    @Primary
    public DynamicDataSource dynamicSource(DataSourceProperties dataSourceProperties) {

        DataSource mainDataSource = initDruidDataSource(dataSourceProperties);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        dynamicDataSource.setTargetDataSources(Map.of("default", mainDataSource));
        dynamicDataSource.afterPropertiesSet();
        dynamicDataSource.setDefaultTargetDataSource(mainDataSource);

        DynamicDataSourceHolder.putMainDataSource(mainDataSource);

        return dynamicDataSource;
    }

    private HikariDataSource initDruidDataSource(DataSourceProperties dataSourceProperties) {
        return DynamicDataSourceHolder.getDataSourceByDataModelDTO(dataSourceProperties);
    }

    @Bean
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(DynamicDataSource dataSource) {

        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    @ConditionalOnMissingBean(TransactionTemplate.class)
    public TransactionTemplate transactionTemplate(DataSourceTransactionManager manager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(manager);
        return transactionTemplate;
    }
}