package org.lcdpframework.server.holder;

import java.util.*;

public class LcdpRequestHolder {

    private static final ThreadLocal<Map<String, Object>> requestHolder = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<List<LinkedHashMap<String, Object>>> responseHolder = ThreadLocal.withInitial(ArrayList::new);

    public static Map<String, Object> getQuery() {
        return requestHolder.get();
    }

    public static void setRequest(Map<String, Object> requestContent) {
        requestHolder.set(requestContent);
    }

    public static List<LinkedHashMap<String, Object>> getResponse() {
        return responseHolder.get();
    }

    public static void setResponse(List<LinkedHashMap<String, Object>> response) {
        responseHolder.set(response);
    }

    public static void clear() {
        requestHolder.remove();
    }
}
