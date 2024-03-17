package org.cyber.universal_auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "permission")
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false, unique = true)
    private String permissionName;

    @ManyToMany()
    @JoinTable(
            name = "permission_and_user",
            joinColumns = @JoinColumn(name = "permission_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
    )
    //@JsonBackReference(value = "user-permission") ** for one to many only
    @JsonIgnoreProperties("permission")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<User> user = new ArrayList<>();


    @ManyToMany()
    @JoinTable(
            name = "permission_and_group",
            joinColumns = @JoinColumn(name = "permission_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id",referencedColumnName = "id")
    )
    @JsonIgnoreProperties("permission")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<PermissionGroup> permissionGroup = new ArrayList<>();
}




//https://bartoszkomin.blogspot.com/2017/01/many-to-many-relation-with-hibernate.html
// uniqueConstraints={@UniqueConstraint(columnNames={"user_id", "book_id"})})