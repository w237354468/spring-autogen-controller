package org.lcdpframework.listener;

import org.lcdpframework.server.event.impl.LcdpAfterExecuteRequestEvent;
import org.lcdpframework.server.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class DictAfterExecuteEventListener {

    private static final Logger logger = LoggerFactory.getLogger(DictAfterExecuteEventListener.class);

    /**
     * condition may configure more dynamic like using mapping id
     */
    @EventListener(classes = LcdpAfterExecuteRequestEvent.class)
    public void customResponseHandle(LcdpAfterExecuteRequestEvent afterExecuteRequestEvent) {

        PageImpl<List> listResponse = afterExecuteRequestEvent.getListResponse();

        logger.info("get response:  {} and do some operations", JacksonUtil.toJson(listResponse.getContent()));
    }
}
