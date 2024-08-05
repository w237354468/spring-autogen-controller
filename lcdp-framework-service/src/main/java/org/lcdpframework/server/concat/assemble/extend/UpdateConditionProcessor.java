package org.lcdpframework.server.concat.assemble.extend;

import org.lcdpframework.conditon.where.SimpleCondition;
import org.lcdpframework.query.UpdateCondition;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.util.AssembleUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class UpdateConditionProcessor implements Function<UpdateCondition, Void> {

    @Override
    public Void apply(UpdateCondition updateCondition) {
        LinkedHashMap<String, String> attrColumnMap = AssembleUtil.getAttrAndColumnInDM(
                LcdpGlobalParamHolder.getDataModel());

        SimpleCondition condition = (SimpleCondition) updateCondition.getCondition();
        condition.setJavaFiledName(attrColumnMap.get(condition.getJavaFiledName()));

        LinkedHashMap<String, Object> newUpdateValue = new LinkedHashMap<>();
        for (Map.Entry<String, Object> stringObjectEntry : updateCondition.getValues().entrySet()) {
            String newKey = attrColumnMap.get(stringObjectEntry.getKey());
            newUpdateValue.put(newKey, stringObjectEntry.getValue());
        }
        updateCondition.setValues(newUpdateValue);
        return null;
    }
}
