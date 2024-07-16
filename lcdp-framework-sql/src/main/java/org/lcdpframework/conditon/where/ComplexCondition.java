package org.lcdpframework.conditon.where;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;

@EqualsAndHashCode(callSuper = true)
@Data
public class ComplexCondition extends RuleCondition{
    private LinkedList<RuleCondition> and = new LinkedList<>();
    private LinkedList<RuleCondition> or = new LinkedList<>();
}
