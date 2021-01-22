package org.tyaa.demo.java.spring.springbootreactjs.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tyaa.demo.java.spring.springbootreactjs.models.Cart;

@Repository
public interface CartMongoDAO extends MongoRepository<Cart, String> {
    Cart findCartByUserId(Long userId);
}
