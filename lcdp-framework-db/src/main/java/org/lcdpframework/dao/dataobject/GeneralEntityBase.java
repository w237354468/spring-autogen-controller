package org.lcdpframework.dao.dataobject;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
public class GeneralEntityBase {

    @Column(name = "created_by")
    protected String createdBy;

    @Column(name = "created_on")
    protected LocalDateTime createdOn;

    @Column(name = "updated_by")
    protected String updatedBy;

    @Column(name = "updated_on")
    protected LocalDateTime updatedOn;

    @Column(name = "has_deleted")
    protected String hasDelete;
}
