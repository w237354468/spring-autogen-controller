package org.lcdp.framework.dao.dataobject.dict;

import jakarta.persistence.*;
import lombok.*;
import org.lcdp.framework.dao.dataobject.GeneralEntityBase;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "data_dict")
public class DataDictEntity extends GeneralEntityBase {

    @Id
    @Column(name = "data_dict_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "dict_name")
    private String name;

    @Column(name = "dict_status")
    private String status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "dataDict", fetch = FetchType.EAGER)
    @OrderBy(value = "detailOrder asc")
    @ToString.Exclude
    private List<DataDictDetailEntity> dictDetails = new ArrayList<>();

    public void addNewDictDetails(DataDictDetailEntity dataDictDetailEntity) {
        dataDictDetailEntity.setDataDict(this);
        this.dictDetails.add(dataDictDetailEntity);
    }
}
