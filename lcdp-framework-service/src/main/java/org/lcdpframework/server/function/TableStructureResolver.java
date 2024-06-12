package org.lcdpframework.server.function;

import org.lcdpframework.server.dto.LcdpDataSourceDTO;
import org.lcdpframework.server.dto.TableDetailInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TableStructureResolver implements Function<LcdpDataSourceDTO, List<TableDetailInfo>> {

    private static final String TYPE_POSTGRES = "Postgres";

    private static final String TYPE_MYSQL = "MySQL";

    @Override
    public List<TableDetailInfo> apply(LcdpDataSourceDTO dataSourceDTO) {

        switch (dataSourceDTO.getDatabaseType()) {
            case TYPE_POSTGRES: {
                return new PostgresTableResolver().getTableInfo(dataSourceDTO);
            }
            case TYPE_MYSQL: {
            }
            default: {
                return new ArrayList<>();
            }
        }
    }
}