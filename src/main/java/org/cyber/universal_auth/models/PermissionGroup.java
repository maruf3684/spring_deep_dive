package org.cyber.universal_auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "permission_group")
@Table(name = "permission_group")
public class PermissionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false, unique = true)
    private String groupName;

    @ManyToMany(mappedBy = "permissionGroup",fetch = FetchType.EAGER)
    @JsonIgnoreProperties("permission_group")
    private List<Permission> permission;

    @ManyToMany()
    @JoinTable(
            name = "user_and_group",
            joinColumns = @JoinColumn(name = "group_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
    )
    @JsonIgnoreProperties("permissions")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<User> user;
}
