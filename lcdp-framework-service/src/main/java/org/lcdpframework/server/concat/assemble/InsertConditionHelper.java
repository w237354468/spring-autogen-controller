package org.lcdpframework.server.concat.assemble;

import org.apache.commons.lang3.StringUtils;
import org.lcdpframework.query.InsertCondition;
import org.lcdpframework.server.dto.DataModelColumnsInfoDTO;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.log.Log;
import org.lcdpframework.server.util.JacksonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.lcdpframework.server.constants.BaseConstants.FLAG_YES;
import static org.lcdpframework.server.log.Log.LOGGER_TYPE.MDL_ADD;
import static org.lcdpframework.server.util.AssembleUtil.*;

public class InsertConditionHelper {

    public static List<InsertCondition> getInsertConditionFromRequest() {

        List<InsertCondition> results = new ArrayList<>();

        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();

        // 入参字段分表 表名 -> 参数分表（JavaAttr）
        Map<String, List<Map<String, Object>>> tableAttrGroup = groupQueryInfoByTable(dataModel);

        for (Map.Entry<String, List<Map<String, Object>>> tableAttr : tableAttrGroup.entrySet()) {
            String tableName = tableAttr.getKey();
            List<Map<String, Object>> insertAttrData = tableAttr.getValue();
            setAutoPrimaryKey(tableName, insertAttrData, dataModel);
            for (Map<String, Object> insertAttrDatum : insertAttrData) {
                InsertCondition insertCondition = new InsertCondition();
                insertCondition.setTable(tableName);
                insertCondition.setValues(insertAttrDatum);
                results.add(insertCondition);
            }
        }
        return results;
    }

    public static List<InsertCondition> getInsertConditionFromDataList(List<Map<String, Object>> data) {

        List<InsertCondition> results = new ArrayList<>();

        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();

        String tableName = dataModel.getJoinInfos().get(0).getMainTable();
        setAutoPrimaryKey(tableName, data, dataModel);
        for (Map<String, Object> insertAttrDatum : data) {
            InsertCondition insertCondition = new InsertCondition();
            insertCondition.setTable(tableName);
            insertCondition.setValues(insertAttrDatum);
            results.add(insertCondition);
        }
        return results;
    }

    private static void setAutoPrimaryKey(String tableName, List<Map<String, Object>> insertAttrData, LcdpDataModelDTO dataModel) {
        String pkTableAttr = getPkTableJavaAttrInDM(tableName, dataModel);
        Map<String, Object> firstInsertData = insertAttrData.get(0);
        if (!firstInsertData.containsKey(pkTableAttr)) {
            DataModelColumnsInfoDTO tablePkInfo = getPkFieldObjInMultiTableDM(dataModel).get(tableName);
            String pkDefaultValue = tablePkInfo.getDefaultValue();
            boolean isPkValueAutoIncrement =
                    (!StringUtils.isEmpty(pkDefaultValue) && pkDefaultValue.startsWith("NEXTVAL")); // auto increment
            if (!isPkValueAutoIncrement && FLAG_YES.equals(tablePkInfo.getInsertPermit())) {
                String javaType = tablePkInfo.getJavaType();
                if (!javaType.equals("02")) {
                    Log.info(MDL_ADD, "field [{}] use auto generate");
                } else {
                    for (Map<String, Object> insertAttrDatum : insertAttrData) {
                        insertAttrDatum.put(pkTableAttr, UUID.randomUUID().toString().replace("-", ""));
                    }
                    Log.info(MDL_ADD, "field [{}] use auto generate, type = [UUID]", JacksonUtil.toJson(pkTableAttr));
                }
            }
        }
    }
}
