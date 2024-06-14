package org.lcdpframework.server.listener.impl;

import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.event.impl.DataModelChangeEvent;
import org.lcdpframework.server.listener.LcdpEventListener;
import org.lcdpframework.server.log.LcdpLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.lcdpframework.server.log.LcdpLog.LOGGER_TYPE.SYSTEM;

@Component
public class DataModelChangeEventListener implements LcdpEventListener<DataModelChangeEvent> {

    @Async
    @Transactional
    @Override
    public void onApplicationEvent(DataModelChangeEvent changeEvent) {

        LcdpLog.printInfo(SYSTEM, "start processing data model change event");
        DataModelChangeEvent.Change modelChange = changeEvent.getModelChange();
        if (!Objects.isNull(modelChange)) {
            LcdpDataModelDTO before = modelChange.getBefore();
            LcdpDataModelDTO after = modelChange.getAfter();

            // TODO update dynamic controllers
            // TODO update cache
        }
        LcdpLog.printInfo(SYSTEM, "finish processing data model change event");
    }
}