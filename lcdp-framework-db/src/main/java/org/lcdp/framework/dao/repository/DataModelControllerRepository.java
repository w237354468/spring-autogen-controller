package org.lcdp.framework.dao.repository;

import org.lcdp.framework.dao.dataobject.api.DataModelControllerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataModelControllerRepository extends JpaRepository<DataModelControllerEntity, String > {
}
