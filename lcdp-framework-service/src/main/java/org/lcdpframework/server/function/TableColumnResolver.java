package org.lcdpframework.server.function;

import jakarta.persistence.EntityManager;
import org.lcdpframework.server.constants.LcdpStringUtil;
import org.lcdpframework.server.datasource.DynamicDataSourceHolder;
import org.lcdpframework.server.dto.DataModelColumnsInfoDTO;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.util.LcdpEventUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.lcdpframework.server.constants.ColumnTypeToJavaType.getTypeIdByColumnType;

public class TableColumnResolver implements Function<LcdpDataSourceDTO, List<DataModelColumnsInfoDTO>> {

    private static final String FIELD_INFO_QUERY = """
            SELECT c.table_name,
                            c.column_name,
                            c.udt_name                                                       as data_type,
                            col_description(c.table_name::regclass, c.ordinal_position::int) AS column_description,
                            CASE WHEN c.is_nullable = 'YES' THEN '1' ELSE '0' END as nullable,
                            CASE WHEN kcu.column_name IS NOT NULL THEN '1' ELSE '0' END       AS is_pk,
                            c.column_default                                                 as data_default
                     FROM information_schema.columns c
                              LEFT JOIN
                          information_schema.table_constraints tc ON c.table_name = tc.table_name AND tc.constraint_type = 'PRIMARY KEY'
                              LEFT JOIN
                          information_schema.key_column_usage kcu
                              ON tc.constraint_name = kcu.constraint_name AND c.column_name = kcu.column_name
                     WHERE c.table_name in (%s)
                     order by c.table_name, c.ordinal_position
            """;

    private final List<String> tableNames;

    public TableColumnResolver(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    @Override
    public List<DataModelColumnsInfoDTO> apply(LcdpDataSourceDTO dataSourceDTO) {

        DynamicDataSourceHolder.changeDataSource(dataSourceDTO);

        ArrayList<DataModelColumnsInfoDTO> result = new ArrayList<>();

        String tableQuerySQL = FIELD_INFO_QUERY.formatted(
                tableNames.stream().map(e -> "'" + e + "'").collect(Collectors.joining(",")));

        EntityManager entityManager = LcdpEventUtil.applicationContext.getBean(EntityManager.class);

        List<Object[]> fieldInfoSqlResultSets = entityManager.createNativeQuery(tableQuerySQL).getResultList();

        for (Object[] fieldInfoSqlResultSet : fieldInfoSqlResultSets) {

            DataModelColumnsInfoDTO columnInfo = new DataModelColumnsInfoDTO();

            columnInfo.setOwnershipTable((String) fieldInfoSqlResultSet[0]);
            columnInfo.setFieldName((String) fieldInfoSqlResultSet[1]);
            columnInfo.setFieldType((String) fieldInfoSqlResultSet[2]);
            columnInfo.setFieldDescribe((String) fieldInfoSqlResultSet[3]);
            columnInfo.setRequired((String) fieldInfoSqlResultSet[4]);
            columnInfo.setPk((String) fieldInfoSqlResultSet[5]);

            columnInfo.setJavaAttr(LcdpStringUtil.toCamel(columnInfo.getFieldName()));
            columnInfo.setJavaType(getTypeIdByColumnType(((String) fieldInfoSqlResultSet[2]).toUpperCase()));

            columnInfo.setInsertPermit(0);
            columnInfo.setUpdatePermit(0);
            columnInfo.setQueryPermit(0);
            columnInfo.setDeletePermit(0);
            columnInfo.setInsertPermit(0);

            columnInfo.setQueryMode("01"); // 01= 02!= 03> 04>= 05< 06<= 07Like 08Between
            columnInfo.setDisplayType("01");// 01.text  02.text-area 03.drop-down  04.radio button  05.check box  06.rich text  07.date range  08.file
            columnInfo.setDefaultValue((String) fieldInfoSqlResultSet[6]);

            columnInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            result.add(columnInfo);
        }
        replaceRepeatJavaAttrs(result);
        return result;
    }

    private void replaceRepeatJavaAttrs(List<DataModelColumnsInfoDTO> list) {
        Set<String> temp = new HashSet<>();
        for (DataModelColumnsInfoDTO column : list) {
            int count = 0;
            StringBuilder attrBuffer = new StringBuilder(column.getJavaAttr());

            while (temp.contains(attrBuffer.toString())) {
                attrBuffer.append(++count);
            }
            column.setJavaAttr(attrBuffer.toString());
            temp.add(attrBuffer.toString());
        }
    }
}
