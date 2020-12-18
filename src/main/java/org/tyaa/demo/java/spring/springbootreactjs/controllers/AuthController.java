package org.tyaa.demo.java.spring.springbootreactjs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.tyaa.demo.java.spring.springbootreactjs.models.ResponseModel;
import org.tyaa.demo.java.spring.springbootreactjs.models.RoleModel;
import org.tyaa.demo.java.spring.springbootreactjs.models.UserModel;
import org.tyaa.demo.java.spring.springbootreactjs.services.AuthService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /* @GetMapping("/roles")
    public ResponseModel getAllRoles() {
        return roleService.getAllRoles();
    } */

    /* @GetMapping("/role/users")
    public ResponseModel getRoleUsers(@RequestParam("id") Long id) {
        return roleService.getRoleUsers(id);
    } */

    /* @GetMapping("/roles/{id}/users")
    public ResponseModel getRoleUsers(@PathVariable("id") Long roleId) {
        return roleService.getRoleUsers(roleId);
    } */

    /* @GetMapping("/roles/{id}/users")
    public ResponseModel getRoleUsers(@PathVariable Long id) {
        return roleService.getRoleUsers(id);
    } */

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/roles")
    public ResponseEntity<ResponseModel> getAllRoles() {
        return new ResponseEntity<>(authService.getAllRoles(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/roles")
    public ResponseEntity<ResponseModel> createRole(@RequestBody RoleModel roleModel) {
        return new ResponseEntity<>(authService.createRole(roleModel), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/admin/roles/{id}")
    public ResponseEntity<ResponseModel> deleteRole(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/admin/roles/{id}/users")
    public ResponseEntity<ResponseModel> getUsersByRole(@PathVariable Long id) {
        ResponseModel responseModel =
                authService.getRoleUsers(id);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getData() != null)
                        ? HttpStatus.OK
                        : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseModel> createUser(@RequestBody UserModel userModel) {
        ResponseModel responseModel =
                authService.createUser(userModel);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getMessage().toLowerCase().contains("created"))
                        ? HttpStatus.CREATED
                        : responseModel.getMessage().contains("name")
                        ? HttpStatus.CONFLICT
                        : HttpStatus.BAD_GATEWAY
        );
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<ResponseModel> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(authService.deleteUser(id), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/user/check")
    @ResponseBody
    public ResponseEntity<ResponseModel> checkUser(Authentication authentication) {
        ResponseModel responseModel = authService.check(authentication);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getData() != null)
                        ? HttpStatus.OK
                        : HttpStatus.UNAUTHORIZED
        );
    }

    @GetMapping("/user/signedout")
    public ResponseEntity<ResponseModel> signedOut(HttpSession httpSession) {
        return new ResponseEntity<>(authService.onSignOut(), HttpStatus.OK);
    }

    @GetMapping("/user/onerror")
    public ResponseEntity<ResponseModel> onError() {
        return new ResponseEntity<>(authService.onError(), HttpStatus.UNAUTHORIZED);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping(value = "/users/{id}/makeadmin")
    public ResponseEntity<ResponseModel> makeUserAdmin(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(authService.makeUserAdmin(id), HttpStatus.OK);
    }
}
