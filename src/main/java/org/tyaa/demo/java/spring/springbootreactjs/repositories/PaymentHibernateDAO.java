package org.tyaa.demo.java.spring.springbootreactjs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tyaa.demo.java.spring.springbootreactjs.entities.Payment;

import java.util.List;

@Repository
public interface PaymentHibernateDAO extends JpaRepository<Payment, Long> {
    List<Payment> findByVendor(String vendor);
}
