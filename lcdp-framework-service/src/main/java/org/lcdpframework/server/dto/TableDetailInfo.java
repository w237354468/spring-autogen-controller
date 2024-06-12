package org.lcdpframework.server.dto;

import lombok.Data;

@Data
public class TableDetailInfo {

    private String dataBaseName;

    private String schemaName;

    private String tableName;

    private String comment;

    private String encoding;
}
