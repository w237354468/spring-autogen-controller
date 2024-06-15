package org.lcdpframework.dao.repository;

import org.lcdpframework.dao.dataobject.model.DataModelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataModelRepository extends JpaRepository<DataModelEntity, String> {
    Page<DataModelEntity> findAllByDataModelNameLike(String dataModelName, PageRequest page);
}
