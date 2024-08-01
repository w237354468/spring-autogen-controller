package org.lcdpframework.sql;

import org.lcdpframework.SqlInfo;
import org.lcdpframework.WwdSQLGenerator;
import org.lcdpframework.query.InsertCondition;
import org.lcdpframework.util.TypeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class InsertSQLAssemble {
    public static LinkedHashMap<String, List<Object>> assembleSQL(List<InsertCondition> insertConditions, Function<InsertCondition, Void> action) {

        LinkedHashMap<String, List<Object>> results = new LinkedHashMap<>();

        WwdSQLGenerator sqlGenerator = new WwdSQLGenerator();
        for (InsertCondition insertCondition : insertConditions) {

            action.apply(insertCondition);

            String insertTable = insertCondition.getTable();
            Map<String, Object> insertValue = insertCondition.getValues();

            StringBuilder insertFieldsBuilder = new StringBuilder().append(' ').append('(');
            StringBuilder insertValuesBuilder = new StringBuilder().append('(');
            for (Map.Entry<String, Object> eachInsert : insertValue.entrySet()) {
                String columnName = eachInsert.getKey();
                insertFieldsBuilder.append(columnName).append(',');

                Object attrValue = TypeUtil.transferSqlValue(eachInsert.getValue());

                insertValuesBuilder.append(attrValue).append(',');
            }
            String insertFieldStr = insertFieldsBuilder.replace(insertFieldsBuilder.length() - 1, insertFieldsBuilder.length(), ")").toString();
            String valueStr = insertValuesBuilder.replace(insertValuesBuilder.length() - 1, insertValuesBuilder.length(), ")").toString();

            SqlInfo end = sqlGenerator.insertInto(insertTable + insertFieldStr).values(valueStr).end();
            String finalSQL = end.getFinalSQL();
            results.put(finalSQL, new ArrayList<>());
            sqlGenerator.reset();
        }
        return results;
    }
}