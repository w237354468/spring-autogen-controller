package org.lcdpframework.dao.repository;

import org.lcdpframework.dao.dataobject.api.DataModelControllerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataModelControllerRepository extends JpaRepository<DataModelControllerEntity, String> {

    Page<DataModelControllerEntity> findAllByControllerNameLike(String controllerName, PageRequest page);
}
