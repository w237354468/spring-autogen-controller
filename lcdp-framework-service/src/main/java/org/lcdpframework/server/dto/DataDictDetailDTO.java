package org.lcdpframework.server.dto;

import lombok.Data;
import org.lcdp.framework.dao.dataobject.dict.DataDictEntity;

@Data
public class DataDictDetailDTO {

    private DataDictEntity dataDict;

    private String value;

    private String display;

    private Integer detailOrder;

    private String status;
}
