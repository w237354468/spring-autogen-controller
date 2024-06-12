package org.lcdp.framework.dao.dataobject;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
public class GeneralEntityBase {

    @Column(name = "created_by")
    protected String createBy;

    @Column(name = "created_time")
    protected LocalDateTime createTime;

    @Column(name = "updated_by")
    protected String updateBy;

    @Column(name = "updated_time")
    protected LocalDateTime updateTime;

    @Column(name = "has_deleted")
    protected String hasDelete;
}
