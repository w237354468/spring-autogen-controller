package org.lcdpframework.dao.dataobject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lcdpframework.dao.dataobject.GeneralEntityBase;

@Getter
@Setter
@ToString
@Entity
@Table(name = "data_model_columns")
public class DataModelColumnEntity extends GeneralEntityBase {

    @Id
    @Column(name = "column_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "data_model_id")
    private String dataModelId;

    @Column(name = "column_name")
    private String fieldName;

    @Column(name = "column_type")
    private String filedType;

    @Column(name = "column_describe")
    private String filedDescribe;

    @Column(name = "java_attr")
    private String javaAttr;

    @Column(name = "java_type")
    private String javaType;

    @Column(name = "pk")
    private String pk;

    @Column(name = "insert_permit")
    private Integer insertPermit;

    @Column(name = "update_permit")
    private Integer updatePermit;

    @Column(name = "delete_permit")
    private Integer deletePermit;

    @Column(name = "query_permit")
    private Integer queryPermit;

    @Column(name = "required")
    private String required;

    @Column(name = "dict_trans_id")
    private String dictTransId;

    @Column(name = "query_mode")
    private String queryMode;

    @Column(name = "displayType")
    private String displayType;

    @Column(name = "default_content")
    private String defaultValue;

    @Column(name = "owner_ship_table")
    private String ownershipTable;
}
