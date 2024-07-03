package org.lcdpframework.listener;

import org.lcdpframework.server.event.impl.LcdpReceiveRequestEvent;
import org.lcdpframework.server.holder.LcdpRequestHolder;
import org.lcdpframework.server.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DictRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(DictRequestListener.class);

    /**
     * condition may configure more dynamic like using mapping id
     */
    @EventListener(classes = LcdpReceiveRequestEvent.class,
            condition = "#requestEvent.mappingUrl.equals('/dictQuery')")
    public void handleDictRequest(LcdpReceiveRequestEvent requestEvent) {

        Map<String, Object> query = requestEvent.getRequestParamMap();
        logger.info(
                "receive new lcdp request which params are {}",
                JacksonUtil.toJson(query)
        );
    }
}
