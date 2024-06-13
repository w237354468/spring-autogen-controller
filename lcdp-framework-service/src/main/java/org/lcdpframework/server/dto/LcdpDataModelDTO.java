package org.lcdpframework.server.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lcdp.framework.dao.dataobject.JoinInfo;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LcdpDataModelDTO extends PageQuery implements Serializable {
    private String id;
    private String dataModelName;
    private String describe;
    private String dataSourceId;
    private List<JoinInfo> joinInfos;
    private List<DataModelColumnDTO> dataModelColumns;
}
