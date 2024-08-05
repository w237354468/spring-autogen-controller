package org.lcdpframework.server.concat.assemble.extend;

import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.lcdpframework.server.concat.assemble.extend.SelectWhereFieldConvert.TABLE_ALIAS_CACHE;

public  class SelectFieldConvert implements Function<List<String>,String> {

    @Override
    public String  apply(List<String> selectFields) {
        if (CollectionUtils.isEmpty(selectFields)) return "*";
        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();
        Map<String, String> tableAliasCache = (Map<String, String>) LcdpGlobalParamHolder.getParam(TABLE_ALIAS_CACHE);

        return dataModel.getDataModelColumns().stream()
                .filter(e -> selectFields.contains(e.getJavaAttr()))
                .map(e -> tableAliasCache.get(e.getOwnershipTable()) + '.' + e.getFieldName() + " as " + e.getJavaAttr())
                .collect(Collectors.joining(", "));
    }
}