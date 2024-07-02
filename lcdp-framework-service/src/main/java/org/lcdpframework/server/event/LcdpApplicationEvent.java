package org.lcdpframework.server.event;

import org.springframework.context.ApplicationEvent;

public abstract class LcdpApplicationEvent extends ApplicationEvent {

    public LcdpApplicationEvent() {
        super("");
    }

    public LcdpApplicationEvent(Object source) {
        super(source);
    }
}