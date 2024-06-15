package org.lcdpframework.dao.dataobject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.lcdpframework.dao.dataobject.GeneralEntityBase;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "data_source")
public class DataSourceEntity extends GeneralEntityBase {

    @Id
    @Column(name = "data_source_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String dataSourceId;

    @OneToMany(mappedBy = "dataSource", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DataModelEntity> dataModelId;

    @Column(name = "data_source_name")
    private String dataSourceName;

    @Column(name = "database_type")
    private String databaseType;

    @Column(name = "data_source_url")
    private String dataSourceUrl;

    @Column(name = "data_source_account")
    private String account;

    @Column(name = "data_source_password")
    private String password;

}
