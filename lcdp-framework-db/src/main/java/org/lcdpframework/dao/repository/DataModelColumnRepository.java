package org.lcdpframework.dao.repository;

import org.lcdpframework.dao.dataobject.model.DataModelColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataModelColumnRepository extends JpaRepository<DataModelColumnEntity, String> {
}
