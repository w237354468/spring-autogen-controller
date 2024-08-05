package org.lcdpframework.server.concat.assemble;

import org.lcdpframework.query.QueryCondition;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.holder.LcdpRequestHolder;
import org.lcdpframework.server.util.AssembleUtil;

import java.util.Map;

public class DetailQueryConditionHelper {

    public static QueryCondition getDetailQueryCondition() {

        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();
        String databaseTable = dataModel.getJoinInfos().get(0).getMainTable();
        Map<String, Object> queryMap = LcdpRequestHolder.getQueryMap();
        String pkTableJavaAttrInDM = AssembleUtil.getPkTableJavaAttrInDM(databaseTable, dataModel);
        if (queryMap.get(pkTableJavaAttrInDM) == null) throw new RuntimeException("cant get primary key");

        return ListQueryConditionHelper.getListQueryCondition();
    }
}
