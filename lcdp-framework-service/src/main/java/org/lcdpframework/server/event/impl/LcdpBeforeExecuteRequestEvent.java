package org.lcdpframework.server.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lcdpframework.server.event.LcdpRequestEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class LcdpBeforeExecuteRequestEvent extends LcdpRequestEvent {

    public LcdpBeforeExecuteRequestEvent(Object source) {
        super(source);
    }
}
