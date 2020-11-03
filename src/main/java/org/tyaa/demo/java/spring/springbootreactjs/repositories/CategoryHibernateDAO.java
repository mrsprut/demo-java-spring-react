package org.tyaa.demo.java.spring.springbootreactjs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tyaa.demo.java.spring.springbootreactjs.entities.Category;

@Repository
public interface CategoryHibernateDAO extends JpaRepository<Category, Long> {
}
