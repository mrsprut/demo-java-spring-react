package org.tyaa.demo.java.spring.springbootreactjs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.tyaa.demo.java.spring.springbootreactjs.entities.Role;
import org.tyaa.demo.java.spring.springbootreactjs.entities.User;
import org.tyaa.demo.java.spring.springbootreactjs.repositories.RoleHibernateDAO;
import org.tyaa.demo.java.spring.springbootreactjs.repositories.UserHibernateDAO;

import java.util.List;

// @Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RoleHibernateDAO roleDAO;

    @GetMapping("/roles")
    // @ResponseBody
    // @RequestMapping(path = "/index", method = RequestMethod.GET)
    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }
}