package org.lcdp.framework.dao.dataobject.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lcdp.framework.dao.dataobject.GeneralEntityBase;
import org.lcdp.framework.dao.dataobject.model.DataModelEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "data_model_controller")
public class DataModelControllersEntity extends GeneralEntityBase {

    @Id
    @Column(name = "controller_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String controllerId;

    @Column(name = "controller_name")
    private String controllerName;

    /**
     * 1. model controller
     * 2. sql controller TODO
     * 3. external controller TODO
     */
    @Column(name = "controller_type")
    private String controllerType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_model_id")
    @ToString.Exclude
    private DataModelEntity dataModel;

    @Column(name = "controller_url")
    private String url;

    @Column(name = "controller_describe")
    private String describe;

    @Column(name = "publish_status")
    private String publishStatus;

    @Column(name = "publish_time")
    private LocalDateTime publishTime;

    @Column(name = "controller_status")
    private String controllerStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "controller_id")
    @ToString.Exclude
    private List<DataModelControllerMappingsEntity> mappingsEntityList = new ArrayList<>();

    public void addControllerMapping(DataModelControllerMappingsEntity dataModelControllerMappingsEntity) {
        this.mappingsEntityList.add(dataModelControllerMappingsEntity);
    }
}
