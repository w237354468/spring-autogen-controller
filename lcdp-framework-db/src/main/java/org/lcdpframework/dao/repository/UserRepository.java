package org.lcdpframework.dao.repository;

import org.lcdpframework.dao.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}