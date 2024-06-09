package org.lcdp.framework.dao.dataobject.model;

import jakarta.persistence.*;
import lombok.*;
import org.lcdp.framework.dao.dataobject.GeneralEntityBase;

@Getter
@Setter
@ToString
@Entity
@Table(name = "data_model_columns")
public class DataModelColumnsEntity extends GeneralEntityBase {

    @Id
    @Column(name = "column_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "data_model_id")
    private String dataModelId;

    @Column(name = "column_name")
    private String fieldName;

    @Column(name = "java_attr")
    private String javaAttr;

    @Column(name = "insert_permit")
    private Integer insertPermit;

    @Column(name = "update_permit")
    private Integer updatePermit;

    @Column(name = "delete_permit")
    private Integer deletePermit;

    @Column(name = "query_permit")
    private Integer queryPermit;

    @Column(name = "dict_trans_id")
    private String dictTransId;

    @Column(name = "displayType")
    private String displayType;
}
