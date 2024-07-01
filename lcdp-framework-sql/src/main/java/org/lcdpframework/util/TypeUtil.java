package org.lcdpframework.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TypeUtil {

    public static Object transferSqlValue(Object field) {
        if (field instanceof String) {
            return "'" + field + "'";
        } else if (field instanceof LocalDateTime) {
            LocalDateTime dateTime = (LocalDateTime) field;
            return "'" + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "'";
        } else if (field instanceof LocalDate) {
            LocalDate date = (LocalDate) field;
            return "'" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'";
        }
        return field;
    }
}
