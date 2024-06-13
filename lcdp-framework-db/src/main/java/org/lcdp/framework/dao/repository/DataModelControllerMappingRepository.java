package org.lcdp.framework.dao.repository;

import org.lcdp.framework.dao.dataobject.api.DataModelControllerMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataModelControllerMappingRepository extends JpaRepository<DataModelControllerMappingEntity, String> {
}
