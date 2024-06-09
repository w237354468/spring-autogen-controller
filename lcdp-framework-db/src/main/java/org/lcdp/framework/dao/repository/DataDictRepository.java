package org.lcdp.framework.dao.repository;

import org.lcdp.framework.dao.dataobject.dict.DataDictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDictRepository extends JpaRepository<DataDictEntity, String> {
}
