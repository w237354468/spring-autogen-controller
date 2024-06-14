package org.lcdpframework.server.datasource;

public class DynamicDataSource extends LcdpDynamicDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (null != null) { // TODO Is lcdp request
            return DynamicDataSourceHolder.getDbKey();
        } else {
            return "default";
        }
    }
}