package org.tyaa.demo.java.spring.springbootreactjs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tyaa.demo.java.spring.springbootreactjs.entities.Role;
import org.tyaa.demo.java.spring.springbootreactjs.models.RoleModel;
import org.tyaa.demo.java.spring.springbootreactjs.repositories.RoleHibernateDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleHibernateDAO roleDOA;

    public List<RoleModel> getAllRoleModel() {
        List<Role> roleList = roleDOA.findAll();
        List<RoleModel> roleModelList = convert(roleList);
        return roleModelList;
    }

    // перенести метод в Role ?
    private List<RoleModel> convert(List<Role> roleList) {
        return roleList.stream()
                .map(role -> RoleModel.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
