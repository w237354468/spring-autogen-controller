package org.lcdpframework.server.listener.impl;

import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.event.impl.DataSourceChangeEvent;
import org.lcdpframework.server.listener.LcdpEventListener;
import org.springframework.stereotype.Component;

@Component
public class DataSourceChangeEventListener implements LcdpEventListener<DataSourceChangeEvent> {

    @Override
    public void onApplicationEvent(DataSourceChangeEvent event) {

        DataSourceChangeEvent.Change change = event.getChange();
        LcdpDataSourceDTO after = change.getAfter();
        LcdpDataSourceDTO before = change.getBefore();

        // TODO replace, add, delete dataSources
//        DynamicDataSourceHolder.removeDataSource();
//        DynamicDataSourceHolder.changeDataSource(after);
    }
}
