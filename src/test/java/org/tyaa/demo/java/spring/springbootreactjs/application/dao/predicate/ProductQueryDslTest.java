package org.tyaa.demo.java.spring.springbootreactjs.application.dao.predicate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tyaa.demo.java.spring.springbootreactjs.SpringbootReactjsApplication;
import org.tyaa.demo.java.spring.springbootreactjs.entities.Product;
import org.tyaa.demo.java.spring.springbootreactjs.repositories.ProductHibernateDAO;
import org.tyaa.demo.java.spring.springbootreactjs.repositories.predicate.ProductPredicatesBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = SpringbootReactjsApplication.class
)
public class ProductQueryDslTest {

    @Autowired
    private ProductHibernateDAO productDAO;

    @Test
    public void givenName_whenGettingListOfORCLProducts_thenCorrect() {
        ProductPredicatesBuilder builder =
            new ProductPredicatesBuilder().with("name", ":", "ORCL");
        List<Product> products =
            (List<Product>) productDAO.findAll(builder.build());
        assertNotNull(products);
        assertEquals(2, products.size());
        products.forEach(product -> assertEquals("ORCL", product.getName()));
    }

    @Test
    public void givenNameAndQuantity_whenGettingListOfORCLProducts_thenCorrect() {
        ProductPredicatesBuilder builder =
            new ProductPredicatesBuilder()
                .with("name", ":", "ORCL")
                .with("quantity", ">", "1500");
        List<Product> products =
                (List<Product>) productDAO.findAll(builder.build());
        assertNotNull(products);
        assertEquals(1, products.size());
        products.forEach(product -> {
            assertEquals("ORCL", product.getName());
            assertTrue(product.getQuantity() > 1500);
        });
    }
}
