package org.lcdpframework.server.listener;

import org.lcdpframework.server.event.LcdpApplicationEvent;
import org.springframework.context.ApplicationListener;

public interface LcdpEventListener<E extends LcdpApplicationEvent> extends ApplicationListener<E> {

}
