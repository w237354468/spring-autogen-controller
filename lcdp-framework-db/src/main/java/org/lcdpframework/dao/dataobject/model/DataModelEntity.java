package org.lcdpframework.dao.dataobject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lcdpframework.dao.dataobject.GeneralEntityBase;
import org.lcdpframework.dao.dataobject.JoinInfo;
import org.lcdpframework.dao.dataobject.api.DataModelControllerEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "data_model")
public class DataModelEntity extends GeneralEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "model_name")
    private String dataModelName;

    @Column(name = "model_describe")
    private String describe;

    @Column(name = "publish_status")
    private String publishStatus;

    @Column(name = "publish_time")
    private LocalDateTime publishTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "data_source_id")
    @ToString.Exclude
    private DataSourceEntity dataSource;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "data_model_id")
    @ToString.Exclude
    private List<JoinInfo> joinInfos = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "data_model_id")
    @OrderBy("updatedOn desc")
    @ToString.Exclude
    private List<DataModelColumnEntity> dataModelColumns = new ArrayList<>();

    @OneToMany(mappedBy = "dataModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<DataModelControllerEntity> controllersList = new ArrayList<>();

    public void addDataModelColumn(DataModelColumnEntity columnsEntity) {
        this.dataModelColumns.add(columnsEntity);
    }

    public void addController(DataModelControllerEntity controllersEntity) {
        this.controllersList.add(controllersEntity);
    }
}