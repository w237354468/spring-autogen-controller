package org.lcdpframework.server.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class LcdpRequestEvent extends LcdpApplicationEvent {

    public LcdpRequestEvent() {
        throw new RuntimeException("can not instance request event with no args");
    }

    public LcdpRequestEvent(Object args) {
        super(args);
    }
}