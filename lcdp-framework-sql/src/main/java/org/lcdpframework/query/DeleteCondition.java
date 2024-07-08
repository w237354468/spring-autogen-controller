package org.lcdpframework.query;

import lombok.Data;
import org.lcdpframework.conditon.where.RuleCondition;

import java.util.Map;

@Data
public class DeleteCondition {

    private String table;

    private Map<String, Object> set;

    private RuleCondition condition;
}
