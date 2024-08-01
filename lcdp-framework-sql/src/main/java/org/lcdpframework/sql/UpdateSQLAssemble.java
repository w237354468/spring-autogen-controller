package org.lcdpframework.sql;

import org.lcdpframework.SqlInfo;
import org.lcdpframework.WwdSQLGenerator;
import org.lcdpframework.conditon.where.SimpleCondition;
import org.lcdpframework.query.UpdateCondition;
import org.lcdpframework.util.TypeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UpdateSQLAssemble {
    public static LinkedHashMap<String, List<Object>> assembleSQL(List<UpdateCondition> updateConditions, Function<UpdateCondition, Void> action) {

        LinkedHashMap<String, List<Object>> result = new LinkedHashMap<>();

        WwdSQLGenerator sqlGenerator = new WwdSQLGenerator();
        for (UpdateCondition updateCondition : updateConditions) {

            action.apply(updateCondition);

            String updateTable = updateCondition.getTable();
            Map<String, Object> updateValue = updateCondition.getValues();

            StringBuilder updateFieldsBuilder = new StringBuilder();
            for (Map.Entry<String, Object> updateData : updateValue.entrySet()) {
                String updateField = updateData.getKey();
                Object updateFieldValue = TypeUtil.transferSqlValue(updateData.getValue());
                updateFieldsBuilder.append(updateField).append(' ').append(" = ").append(updateFieldValue).append(',');
            }
            String updateFieldStr = updateFieldsBuilder.replace(updateFieldsBuilder.length() - 1, updateFieldsBuilder.length(), "").toString();
            SimpleCondition simpleCondition = (SimpleCondition) updateCondition.getCondition();
            Object whereValue = TypeUtil.transferSqlValue(simpleCondition.getValue());

            String whereConditionStr = (simpleCondition.getJavaFiledName() + simpleCondition.getQueryType().operate + whereValue);
            SqlInfo end = sqlGenerator.update(updateTable).set(updateFieldStr).where(whereConditionStr).end();
            String finalSQL = end.getFinalSQL();
            result.put(finalSQL, new ArrayList<>());
            sqlGenerator.reset();;
        }
        return result;
    }
}
