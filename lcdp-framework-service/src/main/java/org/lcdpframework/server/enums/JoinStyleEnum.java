package org.lcdpframework.server.enums;

import java.util.Arrays;

public enum JoinStyleEnum {

    INNER("01"),
    LEFT("02"),
    OUTER("03");

    private String id;

    JoinStyleEnum(String id) {
        this.id = id;
    }

    public static String getJoinById(String pid) {
        return Arrays.stream(JoinStyleEnum.values())
                .filter(e -> e.id.equalsIgnoreCase(pid))
                .findFirst().get().name();
    }
}
