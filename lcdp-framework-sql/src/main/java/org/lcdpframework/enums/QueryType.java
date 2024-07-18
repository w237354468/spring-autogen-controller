package org.lcdpframework.enums;

import java.util.Arrays;
import java.util.Optional;

public enum QueryType {

    EQ("01", "="),
    NOT_EQ("02", "!="),
    GT("03", ">"),
    GT_EQ("04", ">="),
    LT("05", "<"),
    LT_EQ("06", "<="),
    LIKE("07", "LIKE"),
    BETWEEN("08", "BETWEEN"),
    MULTI_EQUAL("11", "IN"),
    MULTI_LIKE("12", "IN"),
    LEFT_LIKE("13", "LIKE"),
    RIGHT_LIKE("14", "LIKE");

    public final String id;
    public final String operate;

    QueryType(String id, String operate) {
        this.id = id;
        this.operate = operate;
    }

    public static QueryType getQueryTypeById(String id) {
        Optional<QueryType> any = Arrays.stream(QueryType.values()).filter(e -> e.id.equals(id)).findAny();
        return any.orElse(null);
    }
}