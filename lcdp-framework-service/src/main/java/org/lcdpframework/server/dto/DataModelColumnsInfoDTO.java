package org.lcdpframework.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataModelColumnsInfoDTO {

    private String id;

    private String fieldName;

    private String fieldType;

    private String fieldDescribe;

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
