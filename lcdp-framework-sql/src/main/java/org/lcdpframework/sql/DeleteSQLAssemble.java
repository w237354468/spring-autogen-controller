package org.lcdpframework.sql;

import org.lcdpframework.WwdSQLGenerator;
import org.lcdpframework.conditon.where.SimpleCondition;
import org.lcdpframework.query.DeleteCondition;
import org.lcdpframework.util.TypeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DeleteSQLAssemble {
    public static LinkedHashMap<String, List<Object>> assembleSQL(List<DeleteCondition> deleteConditions, Function<DeleteCondition, Void> action) {

        LinkedHashMap<String, List<Object>> result = new LinkedHashMap<>();

        WwdSQLGenerator sqlGenerator = new WwdSQLGenerator();
        for (DeleteCondition deleteCondition : deleteConditions) {

            action.apply(deleteCondition);

            String deleteTable = deleteCondition.getTable();
            SimpleCondition simpleCondition = (SimpleCondition) deleteCondition.getCondition();
            Map<String, Object> set = deleteCondition.getSet();

            String deleteSQL = sqlGenerator.update(deleteTable).set(getSetStr(set)).where(getWhereStr(simpleCondition)).end().getFinalSQL();
            result.put(deleteSQL, new ArrayList<>());
            sqlGenerator.reset();
        }
        return result;
    }

    private static String getSetStr(Map<String,Object> set){
        StringBuilder setStrBuilder = new StringBuilder();
        for (Map.Entry<String, Object> setEntry : set.entrySet()) {
            Object value = TypeUtil.transferSqlValue(setEntry.getValue());
            setStrBuilder.append(setEntry.getKey()).append(" = ").append(value).append(',');
        }
        return setStrBuilder.replace(setStrBuilder.length() - 1, setStrBuilder.length(), "").toString();
    }

    private static String getWhereStr(SimpleCondition simpleCondition){
        List<String> deleteIds = new ArrayList<>();
        for (Object o : ((List<?>) simpleCondition.getValue())) {
            deleteIds.add("'" + o + "'");
        }
        String deleteIdStr = "( " + String.join(",", deleteIds) + " )";
        return  (simpleCondition.getJavaFiledName() + " " + simpleCondition.getQueryType().operate + " " + deleteIdStr);
    }
}
