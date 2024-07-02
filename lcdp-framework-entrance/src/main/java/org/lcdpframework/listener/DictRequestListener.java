package org.lcdpframework.listener;

import org.lcdpframework.server.event.impl.LcdpReceiveRequestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DictRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(DictRequestListener.class);

    /**
     * condition may configure more dynamic like using mapping id
     */
    @EventListener(classes = LcdpReceiveRequestEvent.class,
            condition = "#requestEvent.mappingDTO.mappingUrl.startsWith('/dictQuery')")
    public void handleDictRequest(LcdpReceiveRequestEvent requestEvent) {
        logger.info(
                "receive new lcdp request which url is {}",
                requestEvent.getMappingDTO().getMappingUrl()
        );
    }
}
