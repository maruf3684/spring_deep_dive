package org.cyber.universal_auth.controllers;

import org.cyber.universal_auth.dto.user.PermissionDto;
import org.cyber.universal_auth.dto.user.UserDto;
import org.cyber.universal_auth.models.Permission;
import org.cyber.universal_auth.models.PermissionGroup;
import org.cyber.universal_auth.models.User;
import org.cyber.universal_auth.services.user.UserService;
import org.cyber.universal_auth.utils.MapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/user/get_all_user")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDto = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
    @GetMapping("/user/get_all_permission")
    public ResponseEntity<List<PermissionDto>> getAllPermission(){
        List<PermissionDto> permission = userService.getAllPermissionV2();
        return ResponseEntity.status(HttpStatus.OK).body(permission);
    }
    @GetMapping("/user/permission_group")
    public ResponseEntity<List<PermissionGroup>> getAllPermissionGroup(){
        List<PermissionGroup> permission = userService.getAllPermissionGroup();
        return ResponseEntity.status(HttpStatus.OK).body(permission);
    }

}
