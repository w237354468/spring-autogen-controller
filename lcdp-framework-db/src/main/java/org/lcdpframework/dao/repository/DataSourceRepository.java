package org.lcdpframework.dao.repository;

import org.lcdpframework.dao.dataobject.model.DataSourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSourceEntity, String> {

    Page<DataSourceEntity> findAllByDataSourceNameLike(String dataSourceName, Pageable page);
}
