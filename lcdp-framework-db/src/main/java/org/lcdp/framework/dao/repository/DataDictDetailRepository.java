package org.lcdp.framework.dao.repository;

import org.lcdp.framework.dao.dataobject.dict.DataDictDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDictDetailRepository extends JpaRepository<DataDictDetailEntity, String> {
}
