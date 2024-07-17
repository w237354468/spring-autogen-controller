package org.lcdpframework.conditon.table;

import lombok.Data;

@Data
public class Join {

    private String targetTable;

    private String targetAlias;

    private String condition;

    private String type;

    public Join(String childTable, String tableAliasName, String joinStyle) {
        this.targetTable = childTable;
        this.targetAlias = tableAliasName;
        this.type = joinStyle;
    }
}
