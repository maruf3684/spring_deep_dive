package org.cyber.universal_auth.repositories;

import org.cyber.universal_auth.dto.pojo.PermissionDtoRepo;
import org.cyber.universal_auth.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAll();

    @Query(value = "SELECT p.permission_name FROM permission p",nativeQuery = true)
    List<Object[]> getAllPermission();

    @Query(value = "SELECT p.permission_name AS permissionName FROM permission p",nativeQuery = true)
    List<PermissionDtoRepo> getAllPermissionV2();
}
