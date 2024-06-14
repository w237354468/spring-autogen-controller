package org.lcdpframework.server.listener.impl;

import org.lcdpframework.server.datasource.DynamicDataSourceHolder;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.event.impl.DataSourceChangeEvent;
import org.lcdpframework.server.listener.LcdpEventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DataSourceChangeEventListener implements LcdpEventListener<DataSourceChangeEvent> {

    @Override
    public void onApplicationEvent(DataSourceChangeEvent event) {

        DataSourceChangeEvent.Change change = event.getChange();
        LcdpDataSourceDTO after = change.getAfter();
        LcdpDataSourceDTO before = change.getBefore();

        if (Objects.isNull(before)) {
            if (!Objects.isNull(after)) { // add
                DynamicDataSourceHolder.putDataSource(after);
            }
        } else {
            if (Objects.isNull(after)) { // delete
                DynamicDataSourceHolder.removeDataSource(before.getDataSourceId());
            } else { // update
                DynamicDataSourceHolder.putDataSource(after);
            }
        }
    }
}
