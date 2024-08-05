package org.lcdpframework.server.concat.assemble;

import org.lcdpframework.conditon.where.SimpleCondition;
import org.lcdpframework.query.UpdateCondition;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.lcdpframework.enums.QueryType.EQ;
import static org.lcdpframework.server.util.AssembleUtil.getPkTableJavaAttrInDM;
import static org.lcdpframework.server.util.AssembleUtil.groupQueryInfoByTable;

public class UpdateConditionHelper {
    public static List<UpdateCondition> getUpdateCondition() {

        List<UpdateCondition> results = new ArrayList<>();

        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();

        // table name -> （JavaAttr）
        Map<String, List<Map<String, Object>>> tableAttrGroup = groupQueryInfoByTable(dataModel);

        for (Map.Entry<String, List<Map<String, Object>>> tableAttr : tableAttrGroup.entrySet()) {
            String tableName = tableAttr.getKey();
            List<Map<String, Object>> updateAttrData = tableAttr.getValue();

            for (Map<String, Object> updateAttrDatum : updateAttrData) { // each updated data
                UpdateCondition updateCondition = new UpdateCondition();
                updateCondition.setTable(tableName);
                updateCondition.setCondition(getUpdateWhereCondition(tableName, dataModel, updateAttrDatum));
                updateCondition.setValues(updateAttrDatum);
                results.add(updateCondition);
            }
        }
        return results;
    }

    private static SimpleCondition getUpdateWhereCondition(String tableName, LcdpDataModelDTO dataModel,
                                                           Map<String, Object> updateAttrDatum) {
        SimpleCondition simpleCondition = new SimpleCondition();
        // get primary key
        String pkTableAttr = getPkTableJavaAttrInDM(tableName, dataModel);
        Object pkTableAttrValue = updateAttrDatum.remove(pkTableAttr);
        simpleCondition.setValue(pkTableAttrValue);
        simpleCondition.setQueryType(EQ);
        simpleCondition.setJavaFiledName(pkTableAttr);
        return simpleCondition;
    }
}
