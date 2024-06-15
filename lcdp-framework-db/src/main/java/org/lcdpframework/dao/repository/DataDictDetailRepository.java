package org.lcdpframework.dao.repository;

import org.lcdpframework.dao.dataobject.dict.DataDictDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDictDetailRepository extends JpaRepository<DataDictDetailEntity, String> {
}
