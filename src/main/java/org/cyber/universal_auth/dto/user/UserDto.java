package org.cyber.universal_auth.dto.user;

import lombok.*;
import org.cyber.universal_auth.enums.Role;
import org.cyber.universal_auth.enums.UserType;
import org.cyber.universal_auth.models.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
public class UserDto  implements Serializable{
    private String email;
    private String name;
    private Date dateOfBirth;
    private UserType userType;
    private Role role;
    private List<String> permissions;
    private List<String> permissionGroup;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.dateOfBirth = user.getDateOfBirth();
        this.userType = user.getUserType();
        this.role = user.getRole();
        this.permissions = user.getPermissionsAsArray();
        this.permissionGroup = user.getPermissionGroupAsArray();
    }
}