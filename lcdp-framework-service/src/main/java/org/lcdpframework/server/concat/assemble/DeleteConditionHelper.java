package org.lcdpframework.server.concat.assemble;

import org.lcdpframework.conditon.where.RuleCondition;
import org.lcdpframework.conditon.where.SimpleCondition;
import org.lcdpframework.query.DeleteCondition;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.util.LcdpConfigUtil;
import org.springframework.util.StringUtils;

import java.util.*;

import static org.lcdpframework.enums.QueryType.MULTI_EQUAL;
import static org.lcdpframework.server.util.AssembleUtil.getPkTableJavaAttrInDM;
import static org.lcdpframework.server.util.AssembleUtil.groupQueryInfoByTable;

public class DeleteConditionHelper {

    public static List<DeleteCondition> getDeleteCondition() {

        List<DeleteCondition> results = new ArrayList<>();

        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();

        //  table name -> （JavaAttr）
        Map<String, List<Map<String, Object>>> tableAttrGroup = groupQueryInfoByTable(dataModel);

        for (Map.Entry<String, List<Map<String, Object>>> tableAttr : tableAttrGroup.entrySet()) {
            String tableName = tableAttr.getKey();
            Map<String, Object> deleteAttrData = tableAttr.getValue().get(0); // pass an array of primary keys

            DeleteCondition deleteCondition = new DeleteCondition();
            deleteCondition.setTable(tableName);
            deleteCondition.setCondition(getDeleteWhereCondition(tableName, dataModel, deleteAttrData));

            String defaultDelFlagAttr = LcdpConfigUtil.getDefaultDeleteField();
            if (StringUtils.hasText(defaultDelFlagAttr)) {
                Map<String, Object> set = new HashMap<>();
                set.put(defaultDelFlagAttr, "1"); // add del falg
                deleteCondition.setSet(set);
            } else throw new RuntimeException("fail to get del field");
            results.add(deleteCondition);
        }
        return results;
    }

    private static RuleCondition getDeleteWhereCondition(String tableName, LcdpDataModelDTO dataModel, Map<String, Object> deleteAttrDatum) {
        SimpleCondition simpleCondition = new SimpleCondition();
        String pkTableJavaAttrInDM = getPkTableJavaAttrInDM(tableName, dataModel);
        // get primary key
        if (deleteAttrDatum.get(pkTableJavaAttrInDM) instanceof ArrayList) {
            String pkTableAttr = getPkTableJavaAttrInDM(tableName, dataModel);
            Object o = deleteAttrDatum.get(pkTableAttr);
            if (Objects.isNull(o)) throw new RuntimeException("can not get primary key");
            simpleCondition.setValue(o);
            simpleCondition.setQueryType(MULTI_EQUAL);
            simpleCondition.setJavaFiledName(pkTableAttr);
            return simpleCondition;
        } else throw new RuntimeException("delete ids must be array!");
    }
}