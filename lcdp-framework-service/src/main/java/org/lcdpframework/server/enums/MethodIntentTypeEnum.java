package org.lcdpframework.server.enums;

import java.util.Arrays;

public enum MethodIntentTypeEnum {

    OPERATE_INSERT("1"),
    OPERATE_UPDATE("2"),
    OPERATE_DELETE("3"),
    OPERATE_SELECT_DETAIL("4"),
    OPERATE_SELECT_LIST("5"),
    EXPORT_FILE("6"),
    IMPORT_FILE("7");

    public final String id;

    MethodIntentTypeEnum(String id) {
        this.id = id;
    }

    public static MethodIntentTypeEnum getMethodIntentType(String pid) {
        return Arrays.stream(MethodIntentTypeEnum.values())
                .filter(e -> e.id.equalsIgnoreCase(pid))
                .findAny().get();
    }
}
