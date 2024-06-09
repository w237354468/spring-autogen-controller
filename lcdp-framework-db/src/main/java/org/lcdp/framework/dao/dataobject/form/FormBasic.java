package org.lcdp.framework.dao.dataobject.form;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lcdp.framework.dao.dataobject.GeneralEntityBase;

@Entity
@Getter
@Setter
@Table(name = "form_basic")
public class FormBasic extends GeneralEntityBase {

    @Id
    @Column(name = "form_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "form_name")
    private String name;

    @Column(name = "form_status")
    private String status;

    @Column(name = "form_content")
    private String content; // or JacksonNode if often operate

    @Column(name = "form_version")
    private String version;

    @Column(name = "form_url")
    private String url;
}
