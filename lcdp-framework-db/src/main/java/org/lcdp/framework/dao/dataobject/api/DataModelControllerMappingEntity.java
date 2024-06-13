package org.lcdp.framework.dao.dataobject.api;

import jakarta.persistence.*;
import lombok.*;
import org.lcdp.framework.dao.dataobject.GeneralEntityBase;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "data_model_controller_mappings")
public class DataModelControllerMappingEntity extends GeneralEntityBase {

    @Id
    @Column(name = "mapping_id")
    private String id;

    @Column(name = "mapping_name")
    private String name;

    @Column(name = "controller_id")
    private String controllerId;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "mapping_url")
    private String mappingUrl;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mapping_id")
    @ToString.Exclude
    private List<MappingRequestParamEntity> requestParams = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mapping_id")
    @ToString.Exclude
    private List<MappingResponseParamEntity> responsesParams = new ArrayList<>();

    @Column(name = "mapping_describe")
    private String describe;

    public void addRequestParam(MappingRequestParamEntity requestEntity){
        this.requestParams.add(requestEntity);
    }

    public void addResponseParam(MappingResponseParamEntity responseEntity){
        this.responsesParams.add(responseEntity);
    }
}
