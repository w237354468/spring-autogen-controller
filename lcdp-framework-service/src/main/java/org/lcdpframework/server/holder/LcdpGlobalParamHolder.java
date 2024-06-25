package org.lcdpframework.server.holder;

import org.lcdpframework.server.dto.LcdpDataModelDTO;
import org.lcdpframework.server.dto.LcdpMappingDTO;
import org.lcdpframework.server.log.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.lcdpframework.server.constants.BaseConstants.DEFAULT_DATA_SOURCE;

public class LcdpGlobalParamHolder {

    public static final String CURRENT_DATASOURCE = "CURRENT_DATASOURCE";
    public static final String CACHE_DATA_MODEL = "DATA_MODEL";
    public static final String CACHE_MAPPING = "MAPPING";

    private static final InheritableThreadLocal<Map<String, Object>> globalParamHolder =
            new InheritableThreadLocal<>() {
                @Override
                protected Map<String, Object> initialValue() {
                    Map<String, Object> map = new HashMap<>();
                    map.put(CURRENT_DATASOURCE, DEFAULT_DATA_SOURCE);
                    return map;
                }
            };

    public static void clear() {
        globalParamHolder.remove();
    }

    public static LcdpDataModelDTO getDataModel() {
        return (LcdpDataModelDTO) getParam(CACHE_DATA_MODEL);
    }

    public static LcdpMappingDTO getMapping() {
        return (LcdpMappingDTO) getParam(CACHE_MAPPING);
    }

    public static Object getParam(String key) {
        return globalParamHolder.get().get(key);
    }

    public static String getCurrentDatasource() {
        return (String) getParam(CURRENT_DATASOURCE);
    }

    public static void setDataModel(LcdpDataModelDTO dataModel) {
        if (Objects.isNull(dataModel)) {
            Log.error(Log.LOGGER_TYPE.THREAD_LOCAL, "data model cached is null!");
            removeKey(CACHE_DATA_MODEL);
        }
        setParam(CACHE_DATA_MODEL, dataModel);
    }

    public static void setMapping(LcdpMappingDTO mapping) {
        if (Objects.isNull(mapping)) {
            Log.error(Log.LOGGER_TYPE.THREAD_LOCAL, "mapping cached is null!");
            removeKey(CACHE_MAPPING);
        }
        setParam(CACHE_MAPPING, mapping);
    }

    public static void setParam(String key, Object value) {
        globalParamHolder.get().put(key, value);
    }

    public static void removeKey(String key) {
        globalParamHolder.get().remove(key);
    }

    public static void setCurrentDatasource(String currentDatasource) {
        setParam(CURRENT_DATASOURCE, currentDatasource);
    }
}