package org.lcdpframework.server.function;

import org.lcdpframework.server.constants.LcdpStringUtil;
import org.lcdpframework.server.dto.DataModelColumnsInfoDTO;
import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.log.LcdpLog;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;
import java.util.function.Function;

import static org.lcdpframework.server.constants.ColumnTypeToJavaType.getTypeIdByColumnType;
import static org.lcdpframework.server.log.LcdpLog.LOGGER_TYPE.SYSTEM;

public class TableColumnResolver implements Function<LcdpDataSourceDTO, List<DataModelColumnsInfoDTO>> {

    private static final String PRIMARY_SQL_QUERY = """
            SELECT
                a.TABLE_NAME,
                b.COLUMN_NAME PK,
                a.CONSTRAINT_TYPE
            FROM
                user_constraints a
            JOIN
                user_cons_columns b
            ON
                a.constraint_name = b.constraint_name
            WHERE
                    a.CONSTRAINT_type ='P'
                AND
                    a.table_name in (select * from  unnest(?))
            """;

    private static final String FIELD_INFO_QUERY = """
            SELECT 
                A.table_name,
                A.column_name,
                A.data_type,
                A.nullable,
                B.COMMENTS,
                a.data_default,
                a.data_length,
                a.data_precision 
            FROM 
                user_tab_columns A 
            INNER JOIN 
                user_col_comments B 
            ON A.column_name = B.column_name 
            AND A.table_name =  B.table_name 
            and A.table_name in (select * from  unnest(?)) 
            order by array_positions(? ,A.table_name::text), A.COLUMN_ID
            """;

    private final List<String> tableNames;

    public TableColumnResolver(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    @Override
    public List<DataModelColumnsInfoDTO> apply(LcdpDataSourceDTO dataSourceDTO) {

        String url = dataSourceDTO.getDataSourceUrl();
        String account = dataSourceDTO.getAccount();
        String password = dataSourceDTO.getPassword();

        ArrayList<DataModelColumnsInfoDTO> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, account, password);
             PreparedStatement pkSqlStatement = connection.prepareStatement(PRIMARY_SQL_QUERY);
             PreparedStatement fieldInfoSqlStatement = connection.prepareStatement(FIELD_INFO_QUERY)) {

            pkSqlStatement.setArray(1, connection.createArrayOf("text", tableNames.toArray()));
            fieldInfoSqlStatement.setArray(1, connection.createArrayOf("text", tableNames.toArray()));
            fieldInfoSqlStatement.setArray(2, connection.createArrayOf("text", tableNames.toArray()));

            ResultSet pkSqlResultSet = pkSqlStatement.executeQuery();
            ResultSet fieldInfoSqlResultSet = fieldInfoSqlStatement.executeQuery();

            Map<String, String> tablePkInfo = new HashMap<>();
            // fill pk, field into object
            while (pkSqlResultSet.next()) {
                tablePkInfo.put(pkSqlResultSet.getString("TABLE_NAME"), pkSqlResultSet.getString("PK"));
            }

            while (fieldInfoSqlResultSet.next()) {
                DataModelColumnsInfoDTO columnInfo = new DataModelColumnsInfoDTO();

                String tableName = fieldInfoSqlResultSet.getString("table_name");
                columnInfo.setFieldName(fieldInfoSqlResultSet.getObject("column_name").toString());
                columnInfo.setFieldType(fieldInfoSqlResultSet.getObject("data_type").getClass().getName());
                columnInfo.setFieldDescribe(fieldInfoSqlResultSet.getObject("COMMENTS").toString());

                columnInfo.setJavaAttr(LcdpStringUtil.toCamel(columnInfo.getFieldName()));
                columnInfo.setJavaType(getTypeIdByColumnType(fieldInfoSqlResultSet.getObject("data_type").toString()));

                columnInfo.setPk(tablePkInfo.getOrDefault(tableName, "").equals(columnInfo.getFieldName()) ? "1" : "0");
                columnInfo.setRequired("Y".equals(fieldInfoSqlResultSet.getObject("nullable")) ? "N" : "Y");
                columnInfo.setInsertPermit(0);
                columnInfo.setUpdatePermit(0);
                columnInfo.setQueryPermit(0);
                columnInfo.setDeletePermit(0);
                columnInfo.setInsertPermit(0);

//                  TODO default field
//                if (LcdpREConfigUtil.isDefaultField(columnName)) {
//                    map.put("required", );
//                }
                columnInfo.setQueryMode("01"); // 01= 02!= 03> 04>= 05< 06<= 07Like 08Between
                columnInfo.setDisplayType("01");// 01.text  02.text-area 03.drop-down  04.radio button  05.check box  06.rich text  07.date range  08.file
                String dataDefault = fieldInfoSqlResultSet.getString("data_default");
                columnInfo.setDefaultValue(StringUtils.hasLength(dataDefault) ? null : dataDefault.replace("'", ""));

                columnInfo.setOwnershipTable(tableName);
                result.add(columnInfo);
            }
            replaceRepeatJavaAttrs(result);
            return result;
        } catch (SQLException e) {
            LcdpLog.printError(SYSTEM, "retrieve all table fields failed: {}", e.getMessage(), e);
            return result;
        }
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
