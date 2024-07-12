package org.lcdpframework.listener;

import org.lcdpframework.server.event.impl.LcdpReceiveRequestEvent;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.holder.LcdpRequestHolder;
import org.lcdpframework.server.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DictBeforeExecuteRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(DictBeforeExecuteRequestListener.class);

    /**
     * condition may configure more dynamic like using mapping id
     */
    @EventListener(classes = LcdpReceiveRequestEvent.class)
    public void handleDictRequest(LcdpReceiveRequestEvent requestEvent) {

        requestEvent.setContextParam("start", LocalDateTime.now());
        LcdpGlobalParamHolder.setParam("start", LocalDateTime.now());

        Map<String, Object> query = LcdpRequestHolder.getQueryMap();
        logger.info(
                "receive new lcdp request which params are {}",
                JacksonUtil.toJson(query)
        );
    }
}
