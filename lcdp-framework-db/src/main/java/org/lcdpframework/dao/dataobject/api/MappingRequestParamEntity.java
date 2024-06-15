package org.lcdpframework.dao.dataobject.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lcdpframework.dao.dataobject.GeneralEntityBase;

@Getter
@Setter
@ToString
@Entity
@Table(name = "data_model_mapping_request")
public class MappingRequestParamEntity extends GeneralEntityBase {

    @Id
    @Column(name = "mapping_request_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "mapping_id")
    private String mappingId;

    // if reference to any dataModel column
    @Column(name = "column_id")
    private String columnId;

    // location
    // 1. @RequestBody 2. @RequestParam 3.@PathVariable
    @Column(name = "define_type")
    private String defineType;

    // display name on website
    @Column(name = "param_name")
    private String paramName;

    // transfer type when receiving
    @Column(name = "param_type")
    private String paramType;

}
