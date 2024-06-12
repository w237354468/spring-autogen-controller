package org.lcdpframework.server.holder;

import java.util.HashMap;
import java.util.Map;

public class LcdpRequestHolder {

    private static final ThreadLocal<Map<String, Object>> requestHolder = ThreadLocal.withInitial(HashMap::new);

    public static Map<String, Object> getQuery() {
        return requestHolder.get();
    }

    public static void setRequest(Map<String, Object> requestContent) {
        requestHolder.set(requestContent);
    }

    public static void clear() {
        requestHolder.remove();
    }
}
