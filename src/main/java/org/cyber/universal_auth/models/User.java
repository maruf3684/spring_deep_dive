package org.cyber.universal_auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.cyber.universal_auth.enums.Role;
import org.cyber.universal_auth.enums.UserType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private String name;

    private Date dateOfBirth;

    @Column(name = "is_active",nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToMany(mappedBy = "user",fetch = FetchType.EAGER)
    //@JsonManagedReference(value = "user-permission") ** for one to many only
    @JsonIgnoreProperties("users")
    private List<Permission> permission = new ArrayList<>();

    @ManyToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private List<PermissionGroup> permissionGroup = new ArrayList<>();

    public User(String email, String password, String name) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    @JsonIgnore
    public List<String> getPermissionsAsArray() {
        List<String> arr = new ArrayList<>();
        if (permissionGroup != null) {
            List<String> permissionsFromGroup = this.permissionGroup
                    .stream()
                    .flatMap(p_grp ->
                            p_grp.getPermission().stream()
                                    .map(per->per.getPermissionName()))
                    .toList();
            arr.addAll(permissionsFromGroup);
        }

        if (permission != null) {
            List<String> permission = this.permission
                    .stream()
                    .map(p-> p.getPermissionName())
                    .collect(Collectors.toList());
            arr.addAll(permission);
        }

        return arr;
    }

    @JsonIgnore
    public List<String> getPermissionGroupAsArray() {
        if (permissionGroup == null) {
            return new ArrayList<>();
        }
        return permissionGroup.stream()
                .map(PermissionGroup::getGroupName)
                .collect(Collectors.toList());
    }
}

//https://medium.com/code-with-farhan/spring-security-2023-4110f1e33b47
//https://forum.hibernate.org/viewtopic.php?p=2372504