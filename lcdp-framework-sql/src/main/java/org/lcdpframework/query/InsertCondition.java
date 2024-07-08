package org.lcdpframework.query;

import lombok.Data;

import java.util.Map;

@Data
public class InsertCondition {

    Map<String, Object> values;

    private String table;
}