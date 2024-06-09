package org.lcdp.framework.dao.repository;

import org.lcdp.framework.dao.dataobject.model.DataModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataModelRepository extends JpaRepository<DataModelEntity, String> {
}
