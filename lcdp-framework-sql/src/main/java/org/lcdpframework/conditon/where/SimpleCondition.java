package org.lcdpframework.conditon.where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.lcdpframework.enums.QueryType;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCondition extends RuleCondition{

    private String javaFiledName;

    private QueryType queryType;

    private Object value;
}
