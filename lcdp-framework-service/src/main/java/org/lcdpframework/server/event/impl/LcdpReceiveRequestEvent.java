package org.lcdpframework.server.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lcdpframework.server.event.LcdpRequestEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class LcdpReceiveRequestEvent extends LcdpRequestEvent {

    public LcdpReceiveRequestEvent(String mappingUrl) {
        super(mappingUrl);
        this.mappingUrl = mappingUrl;
    }
}
