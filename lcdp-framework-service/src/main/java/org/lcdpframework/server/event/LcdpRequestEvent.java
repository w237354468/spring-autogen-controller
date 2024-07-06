package org.lcdpframework.server.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class LcdpRequestEvent extends LcdpApplicationEvent {

    protected Map<String, Object> eventContext = new HashMap<>();

    public LcdpRequestEvent() {
        throw new RuntimeException("can not instance request event with no args");
    }

    public LcdpRequestEvent(Object args) {
        super(args);
    }
}