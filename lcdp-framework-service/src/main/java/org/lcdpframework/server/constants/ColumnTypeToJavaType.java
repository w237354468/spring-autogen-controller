package org.lcdpframework.server.constants;

import java.math.BigDecimal;
import java.sql.Clob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ColumnTypeToJavaType {

    private static final Map<String, String> TYPE_MAP = new HashMap<>();
    private static final Map<String, String> TYPE_ID = new HashMap<>();

    static {
        TYPE_MAP.put("ABSTIME", LocalDateTime.class.getName());
        TYPE_MAP.put("BIT", Boolean.class.getName());
        TYPE_MAP.put("BOOL", Boolean.class.getName());
        TYPE_MAP.put("BYTEA", String.class.getName());
        TYPE_MAP.put("CHAR", String.class.getName());
        TYPE_MAP.put("CLOB", Clob.class.getName());
        TYPE_MAP.put("DATE", LocalDate.class.getName());
        TYPE_MAP.put("DOUBLE", Double.class.getName());
        TYPE_MAP.put("FLOAT4", String.class.getName());
        TYPE_MAP.put("FLOAT8", Double.class.getName());
        TYPE_MAP.put("INET", String.class.getName());
        TYPE_MAP.put("INT2", Integer.class.getName());
        TYPE_MAP.put("INT2VECTOR", Integer.class.getName());
        TYPE_MAP.put("INT4", Integer.class.getName());
        TYPE_MAP.put("INT8", Long.class.getName());
        TYPE_MAP.put("INTERVAL", Integer.class.getName());
        TYPE_MAP.put("JSON", String.class.getName());
        TYPE_MAP.put("NAME", String.class.getName());
        TYPE_MAP.put("NUMERIC", BigDecimal.class.getName());
        TYPE_MAP.put("OID", String.class.getName());
        TYPE_MAP.put("OIDVECTOR", String.class.getName());
        TYPE_MAP.put("REGCLASS", String.class.getName());
        TYPE_MAP.put("REGPROC", String.class.getName());
        TYPE_MAP.put("REGROLE", String.class.getName());
        TYPE_MAP.put("TDEKEY", String.class.getName());
        TYPE_MAP.put("TEXT", String.class.getName());
        TYPE_MAP.put("TIMESTAMP", LocalDateTime.class.getName());
        TYPE_MAP.put("TIMESTAMPTZ", LocalDateTime.class.getName());
        TYPE_MAP.put("TINYINT", Integer.class.getName());
        TYPE_MAP.put("VARCHAR", String.class.getName());
        TYPE_MAP.put("XID", String.class.getName());
        TYPE_MAP.put("BLOB", byte[].class.getName());

        TYPE_ID.put(Long.class.getName(), "01");
        TYPE_ID.put(String.class.getName(), "02");
        TYPE_ID.put(Integer.class.getName(), "03");
        TYPE_ID.put(Double.class.getName(), "04");
        TYPE_ID.put(BigDecimal.class.getName(), "05");
        TYPE_ID.put(Float.class.getName(), "07");
        TYPE_ID.put(Boolean.class.getName(), "08");
        TYPE_ID.put(LocalDate.class.getName(), "09");
        TYPE_ID.put(LocalDateTime.class.getName(), "10");
        TYPE_ID.put(Clob.class.getName(), "11");
    }

    public static String getTypeIdByColumnType(String columnType) {
        return TYPE_ID.get(TYPE_MAP.get(columnType));
    }

}
