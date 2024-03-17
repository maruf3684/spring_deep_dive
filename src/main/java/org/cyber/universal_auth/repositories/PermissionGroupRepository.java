package org.cyber.universal_auth.repositories;

import org.cyber.universal_auth.models.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PermissionGroupRepository extends JpaRepository<PermissionGroup,Long> {

}
