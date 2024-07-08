package org.lcdpframework.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.lcdpframework.conditon.OrderCondition;
import org.lcdpframework.conditon.table.JoinRule;
import org.lcdpframework.conditon.where.ComplexCondition;

import java.util.List;

@Data
@AllArgsConstructor
public class QueryCondition {

    /**
     * table join info
     */
    private JoinRule tableInfo;

    /**
     * select fields applied by sql, generally is javaName
     */
    private List<String> selectFields;

    /**
     * where condition in sql, javaName
     */
    private ComplexCondition where;

    /**
     * order info
     */
    private OrderCondition order;
}
