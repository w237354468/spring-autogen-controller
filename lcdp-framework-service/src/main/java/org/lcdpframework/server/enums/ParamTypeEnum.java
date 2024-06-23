package org.lcdpframework.server.enums;

import java.util.Arrays;
import java.util.List;

public enum ParamTypeEnum {

    STRING("1", String.class),
    INTEGER("2", Integer.class),
    FLOAT("3", Float.class),
    LIST("4", List.class);

    private final String id;

    private final Class typeClass;

    ParamTypeEnum(String id, Class typeClass) {
        this.id = id;
        this.typeClass = typeClass;
    }

    public static Class getTypeClass(String typeId) {
        return Arrays.stream(ParamTypeEnum.values())
                .filter(e -> e.id.equalsIgnoreCase(typeId))
                .map(e -> e.typeClass)
                .findAny().get();
    }
}
