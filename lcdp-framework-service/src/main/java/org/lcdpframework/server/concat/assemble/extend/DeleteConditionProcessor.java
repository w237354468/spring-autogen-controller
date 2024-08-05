package org.lcdpframework.server.concat.assemble.extend;

import org.lcdpframework.conditon.where.SimpleCondition;
import org.lcdpframework.query.DeleteCondition;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.util.AssembleUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class DeleteConditionProcessor implements Function<DeleteCondition, Void> {

    @Override
    public Void apply(DeleteCondition deleteCondition) {

        LinkedHashMap<String, String> attrColumnMap = AssembleUtil.getAttrAndColumnInDM(
                LcdpGlobalParamHolder.getDataModel());

        LinkedHashMap<String, Object> newUpdateValue = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : deleteCondition.getSet().entrySet()) {
            newUpdateValue.put(attrColumnMap.get(entry.getKey()), entry.getValue());
        }
        deleteCondition.setSet(newUpdateValue);

        SimpleCondition condition = (SimpleCondition) deleteCondition.getCondition();
        condition.setJavaFiledName(attrColumnMap.get(condition.getJavaFiledName()));
        return null;
    }
}