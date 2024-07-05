package org.lcdpframework.server.holder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LcdpRequestHolder {

    private static final ThreadLocal<LcdpRequestContext> requestHolder = ThreadLocal.withInitial(() -> LcdpRequestContext.builder().build());
    private static final ThreadLocal<List<LinkedHashMap<String, Object>>> responseHolder = ThreadLocal.withInitial(ArrayList::new);

    public static Map<String, Object> getQueryMap() {
        return requestHolder.get().getRequestParamMap();
    }

    public static void setRequestContext(LcdpRequestContext context) {
        requestHolder.set(context);
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
