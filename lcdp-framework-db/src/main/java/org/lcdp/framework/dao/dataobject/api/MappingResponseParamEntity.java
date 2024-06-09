package org.lcdp.framework.dao.dataobject.api;

import jakarta.persistence.*;
import lombok.*;
import org.lcdp.framework.dao.dataobject.GeneralEntityBase;

@Getter
@Setter
@ToString
@Entity
@Table(name = "data_model_mapping_response")
public class MappingResponseParamEntity extends GeneralEntityBase {

    @Id
    @Column(name = "mapping_response_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "mapping_id")
    private String mappingId;

    @Column(name = "column_id")
    private String columnId;

    @Column(name = "define_type")
    private String defineType;

    @Column(name = "param_name")
    private String paramName;
}
