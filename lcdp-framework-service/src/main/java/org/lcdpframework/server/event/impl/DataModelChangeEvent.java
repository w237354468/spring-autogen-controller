package org.lcdpframework.server.event.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.event.LcdpApplicationEvent;

import java.io.Serializable;

/**
 * update controller info under data model
 */
@Getter
public class DataModelChangeEvent extends LcdpApplicationEvent {

    private final Change modelChange;

    public DataModelChangeEvent(Change change) {
        super("dataModelChangeEvent");
        this.modelChange = change;
    }

    public static DataModelChangeEvent of(LcdpDataModelDTO before, LcdpDataModelDTO after) {
        DataModelChangeEvent.Change changeInfo = new DataModelChangeEvent.Change(before, after);
        return new DataModelChangeEvent(changeInfo);
    }

    @Data
    @AllArgsConstructor
    public static class Change implements Serializable {

        private LcdpDataModelDTO before;

        private LcdpDataModelDTO after;
    }
}
