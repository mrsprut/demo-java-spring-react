package org.tyaa.demo.java.spring.springbootreactjs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tyaa.demo.java.spring.springbootreactjs.entities.User;

@Repository
public interface UserHibernateDAO extends JpaRepository<User, Long> {
    User findUserByName(String name);
}
