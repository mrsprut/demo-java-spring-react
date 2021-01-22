package org.tyaa.demo.java.spring.springbootreactjs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.tyaa.demo.java.spring.springbootreactjs.models.CartItem;
import org.tyaa.demo.java.spring.springbootreactjs.models.ResponseModel;
import org.tyaa.demo.java.spring.springbootreactjs.services.CartService;

@Controller
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    // внедрение объекта Authentication через аргумент метода
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<ResponseModel> getCartItems(Authentication authentication) {
        return new ResponseEntity<>(
                cartService.getCartItems(authentication),
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ResponseModel> addCartItemCount(
            @PathVariable("id") Long id,
            Authentication authentication
    ) {
        // вызов метода службы - увеличить число товара в корзине на 1
        ResponseModel response =
                cartService.changeCartItemCount(
                        authentication
                        , id
                        , CartItem.Action.ADD
                );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ResponseModel> subtractCartItemCount(
            @PathVariable("id") Long id,
            Authentication authentication
    ) {
        ResponseModel response =
                cartService.changeCartItemCount(
                        authentication
                        , id
                        , CartItem.Action.SUB
                );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ResponseModel> deleteCartItem(
            @PathVariable("id") Long id,
            Authentication authentication
    ) {
        ResponseModel response =
                cartService.changeCartItemCount(
                        authentication
                        , id
                        , CartItem.Action.REM
                );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
