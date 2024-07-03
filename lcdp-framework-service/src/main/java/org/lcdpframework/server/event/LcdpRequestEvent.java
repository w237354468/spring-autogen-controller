package org.lcdpframework.server.event;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.dto.LcdpMappingDTO;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class LcdpRequestEvent extends LcdpApplicationEvent {

    protected Map<String, Object> requestParamMap;

    protected LcdpDataModelDTO dataModelDTO;

    protected LcdpMappingDTO mappingDTO;

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected String mappingUrl;

    public LcdpRequestEvent() {
        throw new RuntimeException("can not instance request event with no args");
    }

    public LcdpRequestEvent(Object args) {
        super(args);
    }
}