package org.lcdpframework.server.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class LcdpRequestEvent extends LcdpApplicationEvent {

    private Map<String, Object> eventContext = new HashMap<>();

    public Object getContextParam(String key) {
        if (key != null) {
            return eventContext.get(key);
        }
        return null;
    }

    public void setContextParam(String key, Object value) {
        if (key != null) {
            eventContext.put(key, value);
        }
    }

    public LcdpRequestEvent() {
        throw new RuntimeException("can not instance request event with no args");
    }

    public LcdpRequestEvent(Object args) {
        super(args);
    }
}