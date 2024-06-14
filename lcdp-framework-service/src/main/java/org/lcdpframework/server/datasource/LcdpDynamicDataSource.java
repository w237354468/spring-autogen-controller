package org.lcdpframework.server.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * solve circle dependence problem
 */
public abstract class LcdpDynamicDataSource extends AbstractRoutingDataSource {
}