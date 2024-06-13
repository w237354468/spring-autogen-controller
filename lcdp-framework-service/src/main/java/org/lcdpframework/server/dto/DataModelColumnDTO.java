package org.lcdpframework.server.dto;

import lombok.Data;

@Data
public class DataModelColumnDTO {

    private String fieldName;
    private String filedType;
    private String filedDescribe;
    private String javaAttr;
    private String javaType;
    private String pk;
    private Integer insertPermit;
    private Integer updatePermit;
    private Integer deletePermit;
    private Integer queryPermit;
    private String required;
    private String dictTransId;
    private String queryMode;
    private String displayType;
    private String defaultValue;
    private String ownershipTable;
}
