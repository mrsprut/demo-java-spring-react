package org.tyaa.demo.java.spring.springbootreactjs.services.interfaces;

import org.tyaa.demo.java.spring.springbootreactjs.models.CategoryModel;
import org.tyaa.demo.java.spring.springbootreactjs.models.ResponseModel;

public interface ICategoryService {
    ResponseModel create(CategoryModel categoryModel);
    ResponseModel getAll();
    ResponseModel delete(Long id);
}
