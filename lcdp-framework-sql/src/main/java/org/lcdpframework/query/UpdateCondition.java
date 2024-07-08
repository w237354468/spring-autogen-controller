package org.lcdpframework.query;

import lombok.Data;
import org.lcdpframework.conditon.where.RuleCondition;

import java.util.Map;

@Data
public class UpdateCondition {

    private String table;

    Map<String,Object> values;

    private RuleCondition condition;
}