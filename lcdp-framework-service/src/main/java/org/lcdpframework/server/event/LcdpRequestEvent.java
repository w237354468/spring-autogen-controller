package org.lcdpframework.server.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class LcdpRequestEvent extends LcdpApplicationEvent {

    protected LcdpDataModelDTO dataModelDTO;

    protected LcdpMappingDTO mappingDTO;

    protected String mappingUrl;

    public LcdpRequestEvent(Object source) {
        super(source);
        this.mappingDTO = LcdpGlobalParamHolder.getMapping();
        this.dataModelDTO = LcdpGlobalParamHolder.getDataModel();
    }
}
