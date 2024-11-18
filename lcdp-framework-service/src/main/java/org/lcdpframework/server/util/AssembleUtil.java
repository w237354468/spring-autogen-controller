package org.lcdpframework.server.util;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.lcdpframework.dao.dataobject.JoinInfo;
import org.lcdpframework.dao.dataobject.api.MappingRequestParamEntity;
import org.lcdpframework.server.dto.DataModelColumnsInfoDTO;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.holder.LcdpRequestHolder;
import org.lcdpframework.server.log.Log;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.lcdpframework.server.constants.BaseConstants.FLAG_YES;
import static org.lcdpframework.server.log.Log.LOGGER_TYPE.ASSEMBLE;

public class AssembleUtil {
    private static final String CHILD_TABLE_PREFIX = "$CHILD$";

    // ------------------------------------------------
    // ---------------- dm operation ----------------
    // ------------------------------------------------

    // retrieve all tables from data model
    public static List<String> getAllTableNamesInDM(LcdpDataModelDTO dataModel) {

        List<String> result = new LinkedList<>();

        for (JoinInfo joinInfo : dataModel.getJoinInfos()) {
            String mainTable = joinInfo.getMainTable();
            String childTable = joinInfo.getChildTable();
            if (!result.contains(mainTable)) {
                result.add(mainTable);
            }
            if (!result.contains(childTable)) {
                result.add(childTable);
            }
        }
        return result;
    }

    // ------------------------------------------------
    // ------------- javaAttrs operation-------------
    // ------------------------------------------------

    // check whether a field included by data model
    public static boolean dataModelContainsJavaAttr(
            LcdpDataModelDTO dataModel, String javaAttr, String... tables) {
        if (tables == null || tables.length != 1) {
            return retrieveJavaAttrsGroupByTable(dataModel).values().stream()
                    .anyMatch(e -> javaAttr.equals(String.valueOf(e)));
        } else {
            return retrieveJavaAttrsGroupByTable(dataModel).get(tables[0]).stream()
                    .anyMatch(e -> javaAttr.equals(String.valueOf(e)));
        }
    }

    // 获取数据模型中所有的【Java属性名】，可能为多表
    public static Map<String, List<String>> retrieveJavaAttrsGroupByTable(LcdpDataModelDTO dataModel) {
        return dataModel.getDataModelColumns().stream()
                .collect(
                        Collectors.groupingByConcurrent(
                                DataModelColumnsInfoDTO::getOwnershipTable,
                                Collectors.mapping(DataModelColumnsInfoDTO::getJavaAttr, Collectors.toList())));
    }

    public static Map<String, Map<String, String>> getAllJavaAttrsAndColumnNameByMultiDM(
            LcdpDataModelDTO dataModel) {
        Map<String, List<DataModelColumnsInfoDTO>> columnInfosGroupByTable = dataModel.getDataModelColumns()
                .stream().collect(Collectors.groupingBy(DataModelColumnsInfoDTO::getOwnershipTable));

        Map<String, Map<String, String>> result = new HashMap<>();
        for (Map.Entry<String, List<DataModelColumnsInfoDTO>> columnInfoInSingleTable : columnInfosGroupByTable.entrySet()) {
            String tableName = columnInfoInSingleTable.getKey();
            Map<String, String> tableField = columnInfoInSingleTable.getValue()
                    .stream().map(e -> Pair.of(e.getFieldName(), e.getJavaAttr()))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
            result.put(tableName, tableField);
        }
        return result;
    }

    // retrieve all javaAttrs and columnNames from data model
    public static Map<String, String> getAllJavaAttrsAndColumnNameBySingleDM(
            LcdpDataModelDTO dataModel) {
        return dataModel.getDataModelColumns().stream()
                .map(e -> Pair.of(e.getJavaAttr(), e.getFieldName()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    // -----------------------------------------------------------------
    // ------------------ table.column operation --------------------
    // -----------------------------------------------------------------

    // 获取数据模型中所有的javaAttr 和 columnName
    public static Map<String, String> getAllColumnNameAndJavaAttrsBySingleDM(
            LcdpDataModelDTO dataModel) {
        return dataModel.getDataModelColumns().stream()
                .map(e -> Pair.of(e.getFieldName(), e.getJavaAttr()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    // 获取【数据模型】中每个【javaAttr】对应的【column】
    public static LinkedHashMap<String, String> getAttrAndColumnInDM(LcdpDataModelDTO dataModel) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        if (Objects.nonNull(dataModel)) {
            for (DataModelColumnsInfoDTO e : dataModel.getDataModelColumns()) {
                result.put(e.getJavaAttr(), e.getFieldName());
            }
        }
        return result;
    }

    // 获取【数据模型】中每个【javaAttr】对应的【table.column】
    public static Map<String, String> getAttrAndTableColumnInDM(LcdpDataModelDTO dataModel) {
        Map<String, String> result = new HashMap<>();
        if (Objects.nonNull(dataModel)) {
            for (DataModelColumnsInfoDTO e : dataModel.getDataModelColumns()) {
                result.put(e.getJavaAttr(), e.getOwnershipTable() + "." + e.getFieldName());
            }
        }
        return result;
    }

    // 获取数据模型中的【主键字段对象】，返回 table -> pkQO
    public static Map<String, DataModelColumnsInfoDTO> getPkFieldObjInMultiTableDM(
            LcdpDataModelDTO dataModel) {
        Map<String, DataModelColumnsInfoDTO> pkFields = new HashMap<>();
        for (DataModelColumnsInfoDTO DataModelColumnsInfoDTO : dataModel.getDataModelColumns()) {
            if (FLAG_YES.equals(DataModelColumnsInfoDTO.getPk())) {
                pkFields.put(DataModelColumnsInfoDTO.getOwnershipTable(), DataModelColumnsInfoDTO);
            }
        }
        if (CollectionUtils.isEmpty(pkFields)) {
            throw new RuntimeException("primary key config error");
        }
        return pkFields;
    }

    // 获取数据模型中的【主键字段名】，多表主键，返回 table -> javaAttr
    public static Map<String, String> getPkTableJavaAttrMultiTableDM(LcdpDataModelDTO dataModel) {
        Map<String, String> pkFields = new HashMap<>();
        Map<String, DataModelColumnsInfoDTO> pkFieldObjInDM = getPkFieldObjInMultiTableDM(dataModel);
        for (Map.Entry<String, DataModelColumnsInfoDTO> entry : pkFieldObjInDM.entrySet()) {
            String tableName = entry.getKey();
            DataModelColumnsInfoDTO pkFieldInfo = entry.getValue();
            pkFields.put(tableName, pkFieldInfo.getJavaAttr());
        }
        return pkFields;
    }

    // 获取数据模型中的【主键字段名】，多表主键，返回 table -> table.column
    public static Map<String, String> getPkTableColumnInMultiTableDM(LcdpDataModelDTO dataModel) {
        Map<String, String> pkFields = new HashMap<>();
        Map<String, DataModelColumnsInfoDTO> pkFieldObjInDM = getPkFieldObjInMultiTableDM(dataModel);
        for (Map.Entry<String, DataModelColumnsInfoDTO> entry : pkFieldObjInDM.entrySet()) {
            String tableName = entry.getKey();
            DataModelColumnsInfoDTO pkFieldInfo = entry.getValue();
            pkFields.put(tableName, tableName + "." + pkFieldInfo.getFieldName());
        }
        return pkFields;
    }

    // 获取数据模型中 [某表] 的【主键字段名称】
    public static String getPkTableJavaAttrInDM(String tableName, LcdpDataModelDTO dataModel) {
        String pkField = getPkTableJavaAttrMultiTableDM(dataModel).get(tableName);
        if (!StringUtils.hasText(pkField)) {
            throw new RuntimeException("primary key error");
        }
        return pkField;
    }

    // 获取数据模型中 [某表] 的【主键字段名称】
    public static String getPkTableColumnInDM(String tableName, LcdpDataModelDTO dataModel) {
        String pkField = getPkTableColumnInMultiTableDM(dataModel).get(tableName);
        if (StringUtils.isEmpty(pkField)) {
            throw new RuntimeException("主键信息有误");
        }
        return pkField;
    }

    // 获取数据模型中的【主键字段名】，单表单主键
    public static String getPkColumnInSingleTableDM(LcdpDataModelDTO dataModel) {
        String tableName = dataModel.getJoinInfos().get(0).getMainTable();
        return getPkTableColumnInDM(tableName, dataModel);
    }

    // 获取数据模型中所有的【column属性名】，可能为多表
    public static List<String> getAllColumnInDMByTable(
            LcdpDataModelDTO dataModel, String tableName) {
        Map<String, List<String>> allColumnInDM = getAllColumnInDM(dataModel);
        List<String> orDefault = allColumnInDM.getOrDefault(tableName, new ArrayList<>());
        if (CollectionUtils.isEmpty(orDefault)) {
            throw new RuntimeException("模型信息配置错误，未找到数据表下字段");
        }
        return orDefault;
    }

    // 获取数据模型中所有的【column属性名】，可能为多表
    public static Map<String, List<String>> getAllColumnInDM(LcdpDataModelDTO dataModel) {
        return dataModel.getDataModelColumns().stream()
                .collect(
                        Collectors.groupingBy(
                                DataModelColumnsInfoDTO::getOwnershipTable,
                                Collectors.mapping(DataModelColumnsInfoDTO::getFieldName, Collectors.toList())));
    }

    // 检查数据模型中【是否包含】某字段【tableColumn】
    public static boolean dmContainsColumn(
            LcdpDataModelDTO dataModel, String columnName, String... tables) {
        if (tables == null || tables.length != 1) {
            return getAllColumnInDM(dataModel).values().stream()
                    .anyMatch(e -> columnName.equals(String.valueOf(e)));
        } else {
            return getAllColumnInDM(dataModel).get(tables[0]).stream()
                    .anyMatch(e -> columnName.equals(String.valueOf(e)));
        }
    }

    // 数据模型中查找所有【重名字段】 返回 table.column 【选取所有】
    public static List<String> getTableColumnByColumnName(
            LcdpDataModelDTO dataModel, String columnName) {
        return dataModel.getDataModelColumns().stream()
                .filter(e -> e.getFieldName().equals(columnName))
                .map(e -> e.getOwnershipTable() + "." + e.getFieldName())
                .collect(Collectors.toList());
    }

    // 将【请求信息】通过数据模型的【字段所属表】【分区】
    public static Map<String, List<Map<String, Object>>> groupQueryInfoByTable(LcdpDataModelDTO dataModel) {

        Map<String, List<Map<String, Object>>> result = new HashMap<>();

        Map<String, Object> queryInfo = LcdpRequestHolder.getDynamicRequestParam();

        Map<String, List<String>> allJavaAttrsGroupByTable = retrieveJavaAttrsGroupByTable(dataModel); // 各表的javaAttr

        for (Map.Entry<String, List<String>> group : allJavaAttrsGroupByTable.entrySet()) {

            String tableName = group.getKey();
            List<String> javaAttrs = group.getValue();

            // sub table data exists
            if (queryInfo.containsKey(CHILD_TABLE_PREFIX + tableName)) {
                List<Map<String, Object>> subTableData =
                        (List<Map<String, Object>>) queryInfo.get(CHILD_TABLE_PREFIX + tableName); // sub map

                List<Map<String, Object>> filterTableResult = new ArrayList<>();
                // 过滤这个表用到的字段
                for (Map<String, Object> eachSubData : subTableData) {

                    Map<String, Object> singleDataFilterResult = new HashMap<>(); // 子表单条数据过滤结果
                    for (Map.Entry<String, Object> entryParam : eachSubData.entrySet()) { // 单条数据
                        String paramKey = entryParam.getKey(); // 字段名
                        Object paramValue = entryParam.getValue(); // 字段值
                        if (javaAttrs.contains(paramKey)) {
                            singleDataFilterResult.put(paramKey, paramValue); // 如果是该表的字段，则添加到过滤结果中
                        }
                    }
                    filterTableResult.add(singleDataFilterResult); // 将这条过滤后的结果添加到表数据中
                }
                result.put(tableName, filterTableResult); // 表数据添加
            }
            // 普通插入
            else {
                Map<String, Object> singleDataFilterResult = new HashMap<>(); // 子表单条数据过滤结果
                for (Map.Entry<String, Object> entryParam : queryInfo.entrySet()) {
                    String paramKey = entryParam.getKey();
                    Object paramValue = entryParam.getValue();
                    if (javaAttrs.contains(paramKey)) {
                        singleDataFilterResult.put(paramKey, paramValue);
                    }
                }
                result.put(tableName, Collections.singletonList(singleDataFilterResult));
            }
        }
        Log.info(ASSEMBLE, "split insert data which grouped to: {}", JacksonUtil.toJsonPrettyFormat(result));
        return result;
    }

    // ------------------------------------------------
    // --------------- 连接器操作 ---------------
    // ------------------------------------------------

    // 获取连接器每个【字段】的【查询方式】
    public static List<Triple<String, String, Object>> getFieldQueryMode() {

        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();
        LcdpMappingDTO mapping = LcdpGlobalParamHolder.getMapping();
        Map<String, Object> query = LcdpRequestHolder.getDynamicRequestParam();

        List<Triple<String, String, Object>> result = new ArrayList<>();

        Map<String, String> attrAndColumnNameByDM = getAttrAndTableColumnInDM(dataModel);

        for (MappingRequestParamEntity inParamDTO : mapping.getRequestParams()) {
            String fieldName = inParamDTO.getParamName();
            String queryMode = inParamDTO.getQueryType();
            String tableColumnName = attrAndColumnNameByDM.get(fieldName);

            if (StringUtils.hasText(tableColumnName)) {
                Object o = query.get(tableColumnName);
                if (Objects.nonNull(o)) {
                    result.add(
                            Triple.of(
                                    tableColumnName,
                                    queryMode,
                                    query.get(tableColumnName))); // javaAttr -> table.column -> queryMode
                }
            } else {
                Log.error(ASSEMBLE, "获取字段查询方式时发生错误，字段未配置所在表信息 [{}]", fieldName);
                throw new RuntimeException("字段未配置所在表信息" + fieldName);
            }
        }
        return result;
    }

    // 提取连接器【入参】参数，所有【javaAttr】
    public static Map<String, Object> filterPermitQueryFields(
            Map<String, Object> queryInfo, LcdpMappingDTO mdlConnector) {

        Set<String> permitQueryJavaAttrs =
                mdlConnector.getRequestParams().stream()
                        .map(MappingRequestParamEntity::getParamName)
                        .collect(Collectors.toSet());
        Log.info(
                ASSEMBLE,
                "连接器 [{}] 配置中允许 [查询] 的参数名为: {}",
                mdlConnector.getDescribe(),
                JacksonUtil.toJson(permitQueryJavaAttrs));

        Map<String, Object> filteredQueryInfo =
                queryInfo.entrySet().stream()
                        .filter(e -> permitQueryJavaAttrs.contains(e.getKey()))
                        .filter(e -> !StringUtils.isEmpty(e.getValue()))
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue)); // 提取请求中所有能用于查询的字段，结果内容为  javaAttr -> value
        Log.info(
                ASSEMBLE, "[过滤后] 请求中可用于 [查询] 的参数为: {}", JacksonUtil.toJsonPrettyFormat(filteredQueryInfo));

        return filteredQueryInfo;
    }

    // 将请求中的 【paramKey】 转换为 【table.column】为字段添加数据模型
    public static void transferQueryParamToTable(
            Map<String, Object> queryInfo, LcdpDataModelDTO dataModel) {
        Map<String, Object> transferFieldInfo = new HashMap<>();
        Set<String> removeKeys = new HashSet<>();
        out:
        for (Map.Entry<String, Object> paramInfo : queryInfo.entrySet()) {
            String paramKey = paramInfo.getKey();
            Object paramValue = paramInfo.getValue();

            for (DataModelColumnsInfoDTO attrInfo : dataModel.getDataModelColumns()) {
                String columnName = attrInfo.getFieldName();
                String javaAttrName = attrInfo.getJavaAttr();
                String belongToTable = attrInfo.getOwnershipTable();
                if (!StringUtils.hasText(belongToTable)) {
                    Log.error(ASSEMBLE, "格式化入参时发生错误，字段未配置所在表信息 [{}]", javaAttrName);
                    throw new RuntimeException("字段未配置所在表信息" + javaAttrName);
                }
                if (paramKey.equals(javaAttrName)) {
                    transferFieldInfo.put(attrInfo.getOwnershipTable() + "." + columnName, paramValue);
                    removeKeys.add(paramKey);
                    continue out; // 下一个请求字段
                }
            } // 转换入参名称为table.field，如果有重复字段，应当被前端正确传入table.field格式，无需转换
        }
        removeKeys.forEach(queryInfo::remove);
        queryInfo.putAll(transferFieldInfo); // 替换被转换的参数
        Log.info(ASSEMBLE, "HTTP请求参数格式化为: {}", JacksonUtil.toJsonPrettyFormat(queryInfo));
    }

    // 在请求中设置【删除标识】的【默认值】
    public static void fillGenericDeleteFieldToQueryInfo(
            Map<String, Object> finalQueryInfo,
            LcdpDataModelDTO dataModel,
            String tableName,
            String delValue) {
        Map<String, List<String>> tableColumns = getAllColumnInDM(dataModel);

        String defaultDelFlagFieldName = LcdpConfigUtil.getDefaultDeleteField();
        if (tableColumns.get(tableName).contains(defaultDelFlagFieldName)
                && !finalQueryInfo.containsKey(tableName + "." + defaultDelFlagFieldName)) {
            String key = defaultDelFlagFieldName;
            finalQueryInfo.put(tableName + "." + key, delValue);
            Log.info(ASSEMBLE, "自动填充 [{}] 表 [删除标识] 字段 [{}] 为=> [{}]", tableName, key, delValue);
            return;
        }
    }
}
