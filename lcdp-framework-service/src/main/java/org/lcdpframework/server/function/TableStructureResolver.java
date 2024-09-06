package org.lcdpframework.server.function;

import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.dto.TableDetailInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TableStructureResolver implements Function<LcdpDataSourceDTO, List<TableDetailInfo>> {

    private static final String TYPE_POSTGRES = "postgres";

    private static final String TYPE_MYSQL = "mysql";

    @Override
    public List<TableDetailInfo> apply(LcdpDataSourceDTO dataSourceDTO) {

        switch (dataSourceDTO.getDatabaseType().toLowerCase()) {
            case TYPE_POSTGRES: {
                return PostgresTableResolver.getTableInfo(dataSourceDTO);
            }
            case TYPE_MYSQL: {
                // TODO
            }
            default: {
                return new ArrayList<>();
            }
        }
    }
}