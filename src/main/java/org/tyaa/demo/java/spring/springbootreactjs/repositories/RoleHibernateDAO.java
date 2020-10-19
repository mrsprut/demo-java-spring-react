package org.tyaa.demo.java.spring.springbootreactjs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tyaa.demo.java.spring.springbootreactjs.entities.Role;

@Repository
public interface RoleHibernateDAO extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
