package org.lcdpframework.server.concat.assemble.extend;

import org.lcdpframework.query.InsertCondition;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.util.AssembleUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class InsertConditionProcessor implements Function<InsertCondition, Void> {

    @Override
    public Void apply(InsertCondition insertCondition) {
        Map<String, Object> insertValue = insertCondition.getValues();

        LinkedHashMap<String, String> attrColumnMap = AssembleUtil.getAttrAndColumnInDM(
                LcdpGlobalParamHolder.getDataModel());

        LinkedHashMap<String,Object > newInsertValue = new LinkedHashMap<>();
        for (Map.Entry<String, Object> insertV : insertValue.entrySet()) {
            String key = insertV.getKey();
            Object value = insertV.getValue();
            newInsertValue.put(attrColumnMap.get(key), value);
        }
        insertCondition.setValues(newInsertValue);
        return null;
    }
}
