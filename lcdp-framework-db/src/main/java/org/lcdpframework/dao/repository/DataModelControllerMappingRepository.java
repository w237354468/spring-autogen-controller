package org.lcdpframework.dao.repository;

import org.lcdpframework.dao.dataobject.api.DataModelControllerMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataModelControllerMappingRepository extends JpaRepository<DataModelControllerMappingEntity, String> {
}
