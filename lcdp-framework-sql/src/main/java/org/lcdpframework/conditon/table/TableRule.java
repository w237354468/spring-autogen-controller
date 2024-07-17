package org.lcdpframework.conditon.table;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableRule {

    private String name;

    private String alias;

    private List<Join> joins = new ArrayList<>();
}
