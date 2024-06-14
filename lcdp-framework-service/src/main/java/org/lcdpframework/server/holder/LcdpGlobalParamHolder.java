package org.lcdpframework.server.holder;

import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.log.LcdpLog;

import java.util.Map;
import java.util.Objects;

public class LcdpGlobalParamHolder {

    public static final String CURRENT_DATASOURCE = "CURRENT_DATASOURCE";
    public static final String CACHE_DATA_MODEL = "DATA_MODEL";

    private static final InheritableThreadLocal<Map<String, Object>> globalParamHolder =
            new InheritableThreadLocal<>() {
                @Override
                protected Map<String, Object> initialValue() {
                    return Map.of(CURRENT_DATASOURCE, "default");
                }
            };

    public static Object getParam(String key) {
        return globalParamHolder.get().get(key);
    }

    public static void setParam(String key, Object value) {
        globalParamHolder.get().put(key, value);
    }

    public static void removeKey(String key) {
        globalParamHolder.get().remove(key);
    }

    public static void clear() {
        globalParamHolder.remove();
    }

    public static void setDataModel(LcdpDataModelDTO dataModel) {
        if (Objects.isNull(dataModel)) {
            LcdpLog.printError(LcdpLog.LOGGER_TYPE.THREAD_LOCAL, "data model cached is null!");
            removeKey(CACHE_DATA_MODEL);
        }
        setParam(CACHE_DATA_MODEL, dataModel);
    }

    public static void setCurrentDatasource(String currentDatasource) {
        setParam(CURRENT_DATASOURCE, currentDatasource);
    }

    public static LcdpDataModelDTO getDataModel() {
        return (LcdpDataModelDTO) getParam(CACHE_DATA_MODEL);
    }

    public static String getCurrentDatasource() {
        return (String) getParam(CURRENT_DATASOURCE);
    }
}