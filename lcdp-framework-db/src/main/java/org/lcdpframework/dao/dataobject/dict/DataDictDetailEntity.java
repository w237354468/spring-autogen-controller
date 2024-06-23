package org.lcdpframework.dao.dataobject.dict;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.lcdpframework.dao.dataobject.GeneralEntityBase;

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
    @JoinColumn(name = "data_dict_id")
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
