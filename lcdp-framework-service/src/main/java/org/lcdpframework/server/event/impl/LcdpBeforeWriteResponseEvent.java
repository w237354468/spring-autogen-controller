package org.lcdpframework.server.event.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lcdpframework.server.event.LcdpResponseEvent;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LcdpBeforeWriteResponseEvent extends LcdpResponseEvent {

    public LcdpBeforeWriteResponseEvent(Object source, PageImpl<List> listResponse, List detailResponse){
        super(source);
        this.listResponse = listResponse;
        this.detailResponse = detailResponse;
    }
}
