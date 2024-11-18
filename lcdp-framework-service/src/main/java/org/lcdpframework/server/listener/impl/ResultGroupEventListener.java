//package org.lcdpframework.server.listener.impl;
//
//import org.lcdpframework.server.event.impl.LcdpBeforeWriteResponseEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.*;
//
//@Component
//public class ResultGroupEventListener {
//
//    @EventListener(LcdpBeforeWriteResponseEvent.class)
//    public void groupTransfer() {
//
//        Map<String, Object> queryMap = LcdpRequestHolder.getQueryMap();
//        TLcdpDmDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();
//
//        if (!CollectionUtils.isEmpty(queryMap)) {
//            if ((Boolean) queryMap.getOrDefault(DETAIL_GROUP_SHOW, Boolean.FALSE)) {
//                log.info("进行结果分组");
//                Map<String, Set<Map<String, Object>>> groupResult = new HashMap<>();
//
//                for (Map<String, Object> result : results) { // 这一层是每条数据
//                    Map<String, List<Map<String, Object>>> map =
//                            AssembleUtil.groupQueryInfoByTable(result, dataModel); // 根据表拆分后的结果
//
//                    for (Map.Entry<String, List<Map<String, Object>>> entry : map.entrySet()) {
//                        String table = entry.getKey();
//                        List<Map<String, Object>> tableData = entry.getValue();
//                        Map<String, Object> data = new HashMap<>();
//                        if (!tableData.isEmpty()) {
//                            data = tableData.get(0);
//                        }
//                        String storageTable = CHILD_TABLE_PREFIX + table;
//                        Set<Map<String, Object>> orDefault =
//                                groupResult.getOrDefault(storageTable, new LinkedHashSet<>());
//                        orDefault.add(data);
//                        groupResult.put(storageTable, orDefault);
//                    }
//                }
//
//                LinkedHashMap<String , Object> result =new LinkedHashMap<>(
//                        JacksonUtil.readMap(JacksonUtil.toJson(groupResult), String.class, Object.class));
//
//                // 主表字段平铺
//                String mainTable = CHILD_TABLE_PREFIX + dataModel.getDatabaseTable();
//                List<Map<String, Object>> o = (List<Map<String, Object>>) result.get(mainTable);
//                if (o != null) {
//                    o.stream().findAny().ifPresent(result::putAll);
//                    result.remove(mainTable);
//                }
//
//                results.clear();
//                results.add(result);
//            }
//        }
//    }
//}
