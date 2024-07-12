package org.lcdpframework.listener;

import org.lcdpframework.server.event.impl.LcdpAfterExecuteRequestEvent;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class DictAfterExecuteEventListener {

    private static final Logger logger = LoggerFactory.getLogger(DictAfterExecuteEventListener.class);

    /**
     * condition may configure more dynamic like using mapping id
     */
    @EventListener(classes = LcdpAfterExecuteRequestEvent.class)
    public void handleDictRequest(LcdpAfterExecuteRequestEvent afterExecuteRequestEvent) {

        LocalDateTime start = (LocalDateTime) LcdpGlobalParamHolder.getParam("start");
        long l = Duration.between(LocalDateTime.now(), start).get(ChronoUnit.NANOS);

        PageImpl<List> response = afterExecuteRequestEvent.getListResponse();
        logger.info(
                "get response:  {}, total consume {} ns",
                JacksonUtil.toJson(response), l
        );
    }
}
