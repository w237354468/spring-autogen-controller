package org.lcdpframework.server.concat.assemble.extend;

import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class SelectWhereFieldConvert implements Function<String, String> {

    public static final String TABLE_ALIAS_CACHE = "lcdp-table-alias-cache";

    /**
     * @param paramName could be a java name like javaAttr or a field name with table name like column_name/table_name
     * @return tableAlias.column_name
     */
    @Override
    public String apply(String paramName) {

        Map<String, String> tableAliasCache = (Map<String, String>) LcdpGlobalParamHolder.getParam(TABLE_ALIAS_CACHE);
        boolean javaAttr = !paramName.contains("/");
        String tableName = javaAttr ? null : paramName.split("/")[1];
        String fieldName = javaAttr ? null : paramName.split("/")[0];

        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();
        Optional<String> any = dataModel.getDataModelColumns().stream()
                .filter(e ->
                        javaAttr
                                ? e.getJavaAttr().equalsIgnoreCase(paramName)
                                : e.getFieldName().equalsIgnoreCase(fieldName))
                .map(e ->
                        javaAttr ?
                                tableAliasCache.get(e.getOwnershipTable()) + '.' + e.getFieldName()
                                : tableAliasCache.get(tableName) + "." + e.getFieldName())
                .findAny();
        return any.orElseThrow(() -> new RuntimeException("can not find appropriate param info: " + paramName));
    }
}
