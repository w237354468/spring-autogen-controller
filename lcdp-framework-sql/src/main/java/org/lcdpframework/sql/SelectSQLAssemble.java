package org.lcdpframework.sql;

import org.lcdpframework.SqlInfo;
import org.lcdpframework.WwdSQLGenerator;
import org.lcdpframework.conditon.OrderCondition;
import org.lcdpframework.conditon.OrderType;
import org.lcdpframework.conditon.table.Join;
import org.lcdpframework.conditon.table.JoinRule;
import org.lcdpframework.conditon.table.TableRule;
import org.lcdpframework.conditon.where.ComplexCondition;
import org.lcdpframework.conditon.where.RuleCondition;
import org.lcdpframework.conditon.where.SimpleCondition;
import org.lcdpframework.query.QueryCondition;
import org.lcdpframework.util.CollectionUtils;
import org.lcdpframework.util.TypeUtil;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;


public class SelectSQLAssemble {

    public static Map.Entry<String, List<Object>> assembleSQL(QueryCondition queryCondition, Function<List<String>, String> selectFieldsConvert,
                                                              Function<String, String> fieldConvertAction) {

        WwdSQLGenerator sqlGenerator = new WwdSQLGenerator();
        JoinRule tableInfo = queryCondition.getTableInfo();

        sqlGenerator.select(selectFieldsConvert.apply(queryCondition.getSelectFields()));
        resolveFromTable(tableInfo, sqlGenerator);
        resolveWhereCondition(fieldConvertAction, sqlGenerator, queryCondition.getWhere());
        resolveOrderCondition(fieldConvertAction, sqlGenerator, queryCondition.getOrder());

        SqlInfo sqlInfo = sqlGenerator.end();
        sqlInfo.replaceAll("\\( and", "(").replaceAll("\\( or", "(").replaceAll("WHERE AND", "WHERE");
        sqlInfo.removeIfEndWith("WHERE ");

        String finalSQL = sqlInfo.getFinalSQL();
        List<Object> params = sqlInfo.getParams();
        return new AbstractMap.SimpleEntry<>(finalSQL, params);
    }

    private static void resolveFromTable(JoinRule tableInfo, WwdSQLGenerator sqlGenerator) {
        if (CollectionUtils.isEmpty(tableInfo.getTables().get(0).getJoins())) {
            TableRule tableRule = tableInfo.getTables().get(0);
            String singleTableName = tableRule.getName();
            String singleTableAlias = tableRule.getAlias();
            sqlGenerator.from(singleTableName + " " + singleTableAlias);
        } else {
            // TODO
            StringBuilder tableRelationStr = new StringBuilder();
            for (TableRule table : tableInfo.getTables()) {
                String alias = table.getAlias();
                String name = table.getName();
                String baseTable = name + ' ' + alias;
                for (Join join : table.getJoins()) {
                    String type = join.getType();
                    String targetTable = join.getTargetTable();
                    String targetAlias = join.getTargetAlias();
                    tableRelationStr.append(baseTable).append(' ').append(type).append(" join ").append(targetTable).append(' ').append(targetAlias)
                            .append(" on ").append(join.getCondition());
                }
            }
            sqlGenerator.from(tableRelationStr.toString());
        }
    }

    private static void resolveOrderCondition(Function<String, String> fieldConvertAction, WwdSQLGenerator sqlGenerator, OrderCondition order) {

        if (order == null) return;
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, OrderType> orderEntry : order.getOrder().entrySet()) {
            String key = fieldConvertAction.apply(orderEntry.getKey());
            OrderType value = orderEntry.getValue();
            builder.append(key).append(' ').append(value).append(',');
        }
        sqlGenerator.orderBy(builder.replace(builder.length() - 1, builder.length(), "").toString());
    }


    private static void resolveWhereCondition(Function<String, String> fieldConvertAction, WwdSQLGenerator sqlGenerator, ComplexCondition condition) {

        if (condition == null || (CollectionUtils.isEmpty(condition.getAnd()) && CollectionUtils.isEmpty(condition.getOr())))
            return;
        sqlGenerator.where(" ");
        StringBuilder stringBuilder = new StringBuilder();
        resolveInnerSql(fieldConvertAction, sqlGenerator, stringBuilder, condition);
        sqlGenerator.and(stringBuilder.toString());
        clearParamHolder();
    }

    private static void resolveInnerSql(Function<String, String> fieldConvertAction, WwdSQLGenerator sqlGenerator, StringBuilder sqlBuilder, RuleCondition ruleCondition) {
        if (ruleCondition.getClass().isAssignableFrom(ComplexCondition.class)) {
            ComplexCondition complexCondition = (ComplexCondition) ruleCondition;
            List<RuleCondition> and = complexCondition.getAnd();
            List<RuleCondition> or = complexCondition.getOr();
            sqlBuilder.append('(');
            if (!CollectionUtils.isEmpty(and)) {
                for (RuleCondition innerCondition : and) {
                    sqlBuilder.append(" and ");
                    resolveInnerSql(fieldConvertAction, sqlGenerator, sqlBuilder, innerCondition);
                }
            }
            if (!CollectionUtils.isEmpty(or)) {
                for (RuleCondition innerCondition : or) {
                    sqlBuilder.append(" or ");
                    resolveInnerSql(fieldConvertAction, sqlGenerator, sqlBuilder, innerCondition);
                }
            }
            sqlBuilder.append(')');
        } else {
            SimpleCondition simpleCondition = (SimpleCondition) ruleCondition;
            String javaFiledName = fieldConvertAction == null ? simpleCondition.getJavaFiledName() : fieldConvertAction.apply(simpleCondition.getJavaFiledName());
            Object value = TypeUtil.transferSqlValue(simpleCondition.getValue());
            sqlBuilder.append(javaFiledName).append(simpleCondition.getQueryType().operate).append(getParamHolder());
            sqlGenerator.param(value);
        }
    }

    private static void clearParamHolder() {
        count.remove();
    }

    private static final ThreadLocal<AtomicInteger> count = ThreadLocal.withInitial(() -> new AtomicInteger(1));

    private static String getParamHolder() {
        return " ?" + count.get().getAndIncrement() + " ";
    }
}
