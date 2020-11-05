package org.tyaa.demo.java.spring.springbootreactjs.services.interfaces;

import org.tyaa.demo.java.spring.springbootreactjs.models.ProductFilterModel;
import org.tyaa.demo.java.spring.springbootreactjs.models.ProductModel;
import org.tyaa.demo.java.spring.springbootreactjs.models.ProductSearchModel;
import org.tyaa.demo.java.spring.springbootreactjs.models.ResponseModel;

public interface IProductService {
    ResponseModel create(ProductModel productModel);
    ResponseModel update(ProductModel productModel);
    ResponseModel getAll();
    ResponseModel delete(Long id);
    ResponseModel getFiltered(ProductFilterModel filter);
    ResponseModel search(ProductSearchModel searchModel);
}
