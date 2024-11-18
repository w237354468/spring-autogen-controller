//package org.lcdpframework.server.listener.impl;
//
//import org.lcdpframework.server.dto.DataModelColumnsInfoDTO;
//import org.lcdpframework.server.dto.LcdpDataModelDTO;
//import org.lcdpframework.server.dto.LcdpMappingDTO;
//import org.lcdpframework.server.event.impl.LcdpBeforeWriteResponseEvent;
//import org.lcdpframework.server.holder.LcdpGlobalParamHolder;
//import org.lcdpframework.server.holder.LcdpRequestHolder;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.lcdpframework.server.enums.MethodIntentTypeEnum.OPERATE_SELECT_LIST;
//
//@Component
//public class DictTransferEventListener {
//
//    @EventListener(LcdpBeforeWriteResponseEvent.class)
//    public void dictTransfer() {
//
//
//        LcdpDataModelDTO dataModel = LcdpGlobalParamHolder.getDataModel();
//        LcdpMappingDTO mapping = LcdpGlobalParamHolder.getMapping();
//
//        String operate = mapping.getMethodIntent();
//
//        // 列表页展示字典数据
//        if (operate.equals(OPERATE_SELECT_LIST.id)) {
//            List<DataModelColumnsInfoDTO> singleSelect =
//                    dataModel.getDataModelColumns().stream()
//                            .filter(e -> e.getDisplayType().equals("03")) // single select
//                            .toList();
//
//            List<DataModelColumnsInfoDTO> multiSelect =
//                    dataModel.getDataModelColumns().stream()
//                            .filter(e -> e.getDisplayType().equals("11")) // multi select
//                            .toList();
//
//            for (DataModelColumnsInfoDTO e : singleSelect) { // 覆盖原始数据
//                String dictType = e.getJavaAttr(); // 列名，使用列名作为dictType
//
//                for (LinkedHashMap<String, Object> record : results) {
//                    String dictValue = (String) record.get(dictType);
//                    record.put(dictType, getDictText(dictType, dictValue));
//                }
//            }
//
//            for (TLcdpDmDataModelAttr e : multiSelect) { // 覆盖原始数据
//                String dictType = e.getJavaAttr(); // 列名，使用列名作为dictType
//
//                for (LinkedHashMap<String, Object> record : results) {
//                    String dictValues = (String) record.get(dictType);
//                    List<String> valueTextResults = new ArrayList<>();
//
//                    if (StringUtils.isEmpty(dictValues)) {
//                        record.put(dictType, valueTextResults); // 数据库存储的dictValue为空
//                        return;
//                    }
//                    String[] split = dictValues.split(",");
//
//                    for (String dictValue : split) {
//                        valueTextResults.add(getDictText(dictType, dictValue));
//                    }
//                    record.put(dictType, valueTextResults);
//                }
//            }
//        } else if (operate.equals(OPERATE_SELECT_DETAIL.getId())) {
//            // 详情页返回Key Value
//            List<TLcdpDmDataModelAttr> multiSelect =
//                    dataModel.getColumnInfo().stream()
//                            .filter(e -> e.getDisplayType().equals("11")) // 返回的字段中为下拉类型的字段
//                            .collect(Collectors.toList());
//
//            for (TLcdpDmDataModelAttr e : multiSelect) {
//                String dictType = e.getJavaAttr(); // 列名，使用列名作为dictType
//
//                for (LinkedHashMap<String, Object> record : results) {
//                    Class<?> dictTypeClass = record.get(dictType).getClass();
//                    if (!dictTypeClass.isAssignableFrom(String.class)) return;
//
//                    String dictValue = (String) record.get(dictType);
//                    if (StringUtils.isEmpty(dictValue)) {
//                        record.put(dictType, new ArrayList<>());
//                    } else {
//                        String[] split = dictValue.split(",");
//                        record.put(dictType, Arrays.asList(split));
//                    }
//                }
//            }
//        }
//    }
//
//    private String getDictText(String dictType, String dictValue) {
//
//        if (StringUtils.isEmpty(dictValue)) {
//            return ""; // 数据库存储的dictValue为空
//        }
//
//        List<String> dictTypes = Collections.singletonList(dictType);
//
//        return LcdpSysDictUtil.getDictTextByTypes(dictTypes, dictValue);
//    }
//}
