package org.tyaa.demo.java.spring.springbootreactjs.services.interfaces;

import org.springframework.security.core.Authentication;
import org.tyaa.demo.java.spring.springbootreactjs.models.ResponseModel;
import org.tyaa.demo.java.spring.springbootreactjs.models.RoleModel;
import org.tyaa.demo.java.spring.springbootreactjs.models.UserModel;

public interface IAuthService {

    public ResponseModel createRole(RoleModel roleModel);

    public ResponseModel createUser(UserModel userModel);

    public ResponseModel getAllRoles();

    public ResponseModel getRoleUsers(Long roleId);

    public ResponseModel deleteUser(Long id);

    public ResponseModel check(Authentication authentication);

    public ResponseModel onSignOut();

    public ResponseModel onError();

    public ResponseModel makeUserAdmin(Long id) throws Exception;
}
