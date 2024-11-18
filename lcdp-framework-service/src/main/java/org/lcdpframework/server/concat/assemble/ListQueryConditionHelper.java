package org.lcdpframework.server.concat.assemble;

import org.lcdpframework.conditon.OrderCondition;
import org.lcdpframework.conditon.OrderType;
import org.lcdpframework.conditon.table.Join;
import org.lcdpframework.conditon.table.JoinRule;
import org.lcdpframework.conditon.table.TableRule;
import org.lcdpframework.conditon.where.ComplexCondition;
import org.lcdpframework.conditon.where.SimpleCondition;
import org.lcdpframework.dao.dataobject.JoinInfo;
import org.lcdpframework.dao.dataobject.api.MappingRequestParamEntity;
import org.lcdpframework.dao.dataobject.api.MappingResponseParamEntity;
import org.lcdpframework.enums.QueryType;
import org.lcdpframework.query.QueryCondition;
import org.lcdpframework.server.concat.assemble.extend.SelectWhereFieldConvert;
import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.lcdpframework.server.enums.ParamTypeEnum;
import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
import org.lcdpframework.server.holder.LcdpRequestHolder;
import org.lcdpframework.server.log.Log;
import org.lcdpframework.server.util.AssembleUtil;
import org.lcdpframework.server.util.JacksonUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lcdpframework.enums.QueryType.*;
import static org.lcdpframework.server.concat.assemble.extend.SelectWhereFieldConvert.TABLE_ALIAS_CACHE;
import static org.lcdpframework.server.constants.BaseConstants.ORDER;
import static org.lcdpframework.server.enums.JoinStyleEnum.getJoinById;
import static org.lcdpframework.server.log.Log.LOGGER_TYPE.MDL_LIST;

public class ListQueryConditionHelper {

    public static ComplexCondition getWhereCondition() {

        Map<String, Object> queryParams = LcdpRequestHolder.getDynamicRequestParam();
        LcdpMappingDTO mapping = LcdpGlobalParamHolder.getMapping();
        List<MappingRequestParamEntity> requestStructure = mapping.getRequestParams();

        ComplexCondition where = new ComplexCondition();

        // resolve request params to column info and sql info
        for (MappingRequestParamEntity paramStructure : requestStructure) {
            String queryTypeId = paramStructure.getQueryType();
            QueryType paramQueryType = QueryType.getQueryTypeById(queryTypeId);

            String paramName = paramStructure.getParamName();
            String paramType = paramStructure.getParamType();
            Object paramValue = queryParams.get(paramName);

            if (paramValue == null || paramValue.toString().isEmpty()) continue; // value is str and empty
            validParamType(paramValue, paramType);

            switch (paramQueryType) {
                case GT:
                case LT:
                case EQ:
                case NOT_EQ:
                case GT_EQ:
                case LT_EQ: {
                    where.getAnd().add(new SimpleCondition(paramName, paramQueryType, paramValue));
                    break;
                }
                case LIKE: {
                    where.getAnd().add(new SimpleCondition(paramName, paramQueryType, "%" + paramValue + '%'));
                }
                case LEFT_LIKE: {
                    where.getAnd().add(new SimpleCondition(paramName, paramQueryType, "%" + paramValue));
                }
                case RIGHT_LIKE: {
                    where.getAnd().add(new SimpleCondition(paramName, paramQueryType, paramValue + "%"));
                }
                case BETWEEN: {
                    List<Object> betweenList = (List<Object>) paramValue;
                    if (!CollectionUtils.isEmpty(betweenList) && betweenList.size() == 2) {
                        Object minValue = betweenList.get(0);
                        Object maxValue = betweenList.get(1);
                        if (Objects.nonNull(minValue)) {
                            where.getAnd().add(new SimpleCondition(paramName, GT_EQ, paramValue));
                        }
                        if (Objects.nonNull(maxValue)) {
                            where.getAnd().add(new SimpleCondition(paramName, LT_EQ, paramValue));
                        }
                        break;
                    } else if (CollectionUtils.isEmpty(betweenList)) {
                        break;
                    } else {
                        throw new RuntimeException("between condition error!");
                    }
                }
                case MULTI_EQUAL: {
                    List<Object> multiEqList = (List<Object>) paramValue;
                    if (!CollectionUtils.isEmpty(multiEqList)) {
                        ComplexCondition or = new ComplexCondition();
                        for (Object o : multiEqList) {
                            or.getOr().add(new SimpleCondition(paramName, EQ, o));
                        }
                        where.getAnd().add(or);
                    }
                    break;
                }
                case MULTI_LIKE: {
                    List<Object> multiLikeList = (List<Object>) paramValue;
                    if (!CollectionUtils.isEmpty(multiLikeList)) {
                        ComplexCondition or = new ComplexCondition();
                        for (Object o : multiLikeList) {
                            or.getOr().add(new SimpleCondition(paramName, LIKE, "%" + o + '%'));
                        }
                        where.getAnd().add(or);
                    }
                    break;
                }
            }
        }
        return where;
    }

    private static void validParamType(Object paramValue, String paramType) {
        Class typeClass = ParamTypeEnum.getTypeClass(paramType);
        if (!paramValue.getClass().isAssignableFrom(typeClass)) {
            throw new RuntimeException("param type error: [{" + paramValue + "}]");
        }
    }

    /**
     * response field selected
     */
    public static List<String> getSelectFields() {

        LcdpMappingDTO mdlConnector = LcdpGlobalParamHolder.getMapping();
        List<MappingResponseParamEntity> responsesParams = mdlConnector.getResponsesParams();
        List<String> selectColumnAlias = new ArrayList<>();
        for (MappingResponseParamEntity param : responsesParams) {
            selectColumnAlias.add(param.getParamName());
        }
        return selectColumnAlias;
    }

    public static JoinRule getTableInfo() {

        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();
        List<JoinInfo> conditions = dataModel.getJoinInfos();
        return composeConditions(conditions);
    }

    private static JoinRule composeConditions(List<JoinInfo> conditionsList) {

        JoinRule joinRule = new JoinRule();

        TableRule tableMainRule = buildJoin(conditionsList);
        joinRule.getTables().add(tableMainRule);

        return joinRule;
    }

    private static TableRule buildJoin(List<JoinInfo> tableConditions) {

        JoinInfo firstJoinInfo = tableConditions.get(0);
        String sourceTable = firstJoinInfo.getMainTable();
        TableRule tableRule = new TableRule();
        tableRule.setName(sourceTable);
        tableRule.setAlias(getTableAliasName(sourceTable));
        if (tableConditions.size() == 1 && !StringUtils.hasText(firstJoinInfo.getChildTable())) {
            return tableRule;
        }

        SelectWhereFieldConvert fieldConvert = new SelectWhereFieldConvert();

        for (JoinInfo multiTableJoinCondition : tableConditions) {

            String mainTable = multiTableJoinCondition.getMainTable();
            String childTable = multiTableJoinCondition.getChildTable();
            String fieldMain = multiTableJoinCondition.getMainTableField();
            String fieldChild = multiTableJoinCondition.getChildTableField();
            String joinStyle = multiTableJoinCondition.getJoinStyle();
            String dictTextByTypes = getJoinById(joinStyle);

            String childAlias = getTableAliasName(childTable);

            Optional<Join> any = tableRule.getJoins().stream().filter(e -> e.getTargetTable().equals(childTable)).findAny();
            Join subJoin = any.orElseGet(() -> new Join(childTable, childAlias, dictTextByTypes));
            String onCondition = fieldConvert.apply( fieldMain+ "/" + mainTable) + "=" +  fieldConvert.apply(fieldChild+"/"+childTable);
            if (!StringUtils.hasText(subJoin.getCondition())) {
                subJoin.setCondition(onCondition);
            } else {
                subJoin.setCondition(subJoin.getCondition() + onCondition);
            }
            if (any.isEmpty()) {
                tableRule.getJoins().add(subJoin);
            }
        }
        return tableRule;
    }

    private static String getTableAliasName(String tableName) {
        Map<String, String> tableAliasCahce = (Map<String, String>) LcdpGlobalParamHolder.getParam(TABLE_ALIAS_CACHE);
        if (tableAliasCahce == null) {
            tableAliasCahce = new HashMap<>();
            LcdpGlobalParamHolder.setParam(TABLE_ALIAS_CACHE, tableAliasCahce);
        }
        String cacheAlias = tableAliasCahce.get(tableName);
        if (cacheAlias != null) {
            return cacheAlias;
        }

        AtomicInteger param = (AtomicInteger) LcdpGlobalParamHolder.getParam("lcdp-table-assemble-count");
        if (param == null) {
            param = new AtomicInteger(0);
            LcdpGlobalParamHolder.setParam("lcdp-table-assemble-count", param);
        }
        String tableAliasName = "t" + param.incrementAndGet();
        tableAliasCahce.put(tableName, tableAliasName);
        return tableAliasName;
    }

    private static OrderCondition getOrder() {
        Map<String, Object> queryMap = LcdpRequestHolder.getDynamicRequestParam();
        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();
        Map<String, String> attrAndTableColumnByDM = AssembleUtil.getAttrAndTableColumnInDM(dataModel);

        Object orderObj = queryMap.get(ORDER);
        if (!ObjectUtils.isEmpty(orderObj)) {
            if (orderObj instanceof ArrayList) {
                ArrayList<Map<String, String>> queryOrder = (ArrayList<Map<String, String>>) orderObj;
                OrderCondition orderCondition = new OrderCondition();
                LinkedHashMap<String, OrderType> order = orderCondition.getOrder();

                if (!CollectionUtils.isEmpty(queryOrder)) {

                    if (!attrAndTableColumnByDM.keySet().containsAll(order.keySet())) {
                        throw new RuntimeException("invalid order param");
                    }
                    queryOrder.stream()
                            .flatMap(e -> e.entrySet().stream())
                            .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue()))
                            .forEach(
                                    e -> {
                                        String value = e.getValue();
                                        OrderType orderTypeBy = OrderType.getOrderTypeBy(value);
                                        order.put(e.getKey(), orderTypeBy);
                                    });
                    Log.info(MDL_LIST, "add custom order param : {}", JacksonUtil.toJson(order));
                    orderCondition.setOrder(order);
                } // order condition
                return orderCondition;
            }
        }
        return null;
    }

    public static QueryCondition getListQueryCondition() {
        ComplexCondition whereCondition = getWhereCondition();
        List<String> selectFields = getSelectFields();
        JoinRule tableInfo = getTableInfo();
        OrderCondition order = getOrder();

        return new QueryCondition(tableInfo, selectFields, whereCondition, order);
    }
}
