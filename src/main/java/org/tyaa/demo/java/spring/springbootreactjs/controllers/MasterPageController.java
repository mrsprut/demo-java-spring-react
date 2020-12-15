package org.tyaa.demo.java.spring.springbootreactjs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MasterPageController {

    @RequestMapping( {
            "/",
            "/shopping",
            "/about",
            "/signin",
            "/signup",
            "/admin",
    })
    public String index() {
        return "index.html";
    }
}
