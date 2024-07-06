package org.lcdpframework.server.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class LcdpResponseEvent extends LcdpApplicationEvent {

    protected Map<String, Object> eventContext;

    protected PageImpl<List> listResponse;

    protected List detailResponse;

    public LcdpResponseEvent(Object source) {
        super(source);
    }

    public LcdpResponseEvent() {
        super("");
    }
}