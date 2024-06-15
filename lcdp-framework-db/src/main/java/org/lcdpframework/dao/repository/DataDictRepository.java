package org.lcdpframework.dao.repository;

import org.lcdpframework.dao.dataobject.dict.DataDictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDictRepository extends JpaRepository<DataDictEntity, String> {
}
