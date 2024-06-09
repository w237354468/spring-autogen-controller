package org.lcdp.framework.dao.dataobject.dict;

import jakarta.persistence.*;
import lombok.*;
import org.lcdp.framework.dao.dataobject.GeneralEntityBase;

@Getter
@Setter
@ToString
@Entity
@Table(name = "data_dict_detail")
public class DataDictDetailEntity extends GeneralEntityBase {

    @Id
    @Column(name = "dict_detail_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String detailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_model_id")
    @ToString.Exclude
    private DataDictEntity dataDict;

    @Column(name = "detail_value")
    private String value;

    @Column(name = "detail_display")
    private String display;

    @Column(name = "detail_order")
    private Integer detailOrder;

    @Column(name = "detail_status")
    private String status;
}
