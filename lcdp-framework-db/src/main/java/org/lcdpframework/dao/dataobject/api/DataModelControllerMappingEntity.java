package org.lcdpframework.dao.dataobject.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lcdpframework.dao.dataobject.GeneralEntityBase;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "mapping_name")
    private String name;

    @Column(name = "controller_id")
    private String controllerId;

    @Column(name = "method_intent")
    private String methodIntent;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "mapping_url")
    private String mappingUrl;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "mapping_id")
    @ToString.Exclude
    private List<MappingRequestParamEntity> requestParams = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "mapping_id")
    @ToString.Exclude
    private List<MappingResponseParamEntity> responseParams = new ArrayList<>();

    @Column(name = "mapping_describe")
    private String describe;

    public void addRequestParam(MappingRequestParamEntity requestEntity) {
        this.requestParams.add(requestEntity);
    }

    public void addResponseParam(MappingResponseParamEntity responseEntity) {
        this.responseParams.add(responseEntity);
    }
}
