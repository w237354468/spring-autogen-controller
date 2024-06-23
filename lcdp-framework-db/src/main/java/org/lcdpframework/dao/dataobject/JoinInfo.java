package org.lcdpframework.dao.dataobject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "join_info")
public class JoinInfo extends GeneralEntityBase {

    @Id
    @Column(name = "join_info_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "data_model_id")
    private String dataModelId;

    @Column(name = "main_table")
    private String mainTable;

    @Column(name = "main_table_column_id")
    private String mainTableFieldId;

    @Column(name = "main_table_column_name")
    private String mainTableField;

    @Column(name = "child_table")
    private String childTable;

    @Column(name = "child_table_column_id")
    private String childTableFieldId;

    @Column(name = "child_table_column_name")
    private String childTableField;

    @Column(name = "join_style")
    private String joinStyle;

    public JoinInfo() {

    }

    public JoinInfo(String mainTable, String mainTableFieldId, String mainTableField, String childTable, String childTableFieldId, String childTableField, String joinStyle) {
        this.mainTable = mainTable;
        this.mainTableFieldId = mainTableFieldId;
        this.mainTableField = mainTableField;
        this.childTable = childTable;
        this.childTableFieldId = childTableFieldId;
        this.childTableField = childTableField;
        this.joinStyle = joinStyle;
    }

    public static JoinInfo singleJoin(String tableName) {
        return new JoinInfo(
                tableName,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
