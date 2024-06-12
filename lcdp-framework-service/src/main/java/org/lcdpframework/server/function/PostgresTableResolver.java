package org.lcdpframework.server.function;

import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.dto.TableDetailInfo;
import org.lcdpframework.server.log.LcdpLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresTableResolver {

    private static final String TABLE_INFO_QUERY =
            """
                    SELECT t.table_catalog, t.table_shcema, t.table_name, d.description
                    FROM
                        information_schema.tables t
                    LEFT JOIN
                        pg_catalog.pg_description d
                    ON
                        t.table_name = pg_catalog.regclass::text
                    WHERE
                        t.table_type = 'BASE TABLE'
                          AND t.table_schema NOT IN ('pg_catalog', 'information_schema');
                    """;

    private static final String ENCODING_QUERY = "SHOW SERVER_encoding";

    public List<TableDetailInfo> getTableInfo(LcdpDataSourceDTO dataSourceDTO) {

        String account = dataSourceDTO.getAccount();
        String password = dataSourceDTO.getPassword();
        String dataSourceUrl = dataSourceDTO.getDataSourceUrl();

        List<TableDetailInfo> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dataSourceUrl, account, password);
             PreparedStatement encodingQuery = connection.prepareStatement(ENCODING_QUERY);
             PreparedStatement tableSchemaQuery = connection.prepareStatement(TABLE_INFO_QUERY)) {

            // encoding info
            ResultSet encodingResult = encodingQuery.executeQuery();
            String serverEncoding = "";
            while (encodingResult.next()) {
                serverEncoding = encodingResult.getObject("SERVER_encoding").toString();
            }

            // table info
            ResultSet tableSchemaResult = tableSchemaQuery.executeQuery();
            while (tableSchemaResult.next()) {
                TableDetailInfo tableDetailInfo = new TableDetailInfo();
                tableDetailInfo.setDataBaseName(tableSchemaResult.getObject("table_catalog").toString());
                tableDetailInfo.setSchemaName(tableSchemaResult.getObject("table_schema").toString());
                tableDetailInfo.setTableName(tableSchemaResult.getObject("table_name").toString());
                tableDetailInfo.setComment(tableSchemaResult.getObject("description").toString());
                tableDetailInfo.setEncoding(serverEncoding);
                result.add(tableDetailInfo);
            }
            return result;
        } catch (SQLException e) {
            LcdpLog.printError(LcdpLog.LOGGER_TYPE.SYSTEM, "connection database failed: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
