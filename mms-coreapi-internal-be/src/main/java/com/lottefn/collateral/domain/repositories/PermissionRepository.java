package com.lottefn.collateral.domain.repositories;

import com.lottefn.collateral.domain.entities.Permission;
import com.lottefn.collateral.domain.enums.Menu;
import com.lottefn.collateral.domain.enums.PermissionMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Page<Permission> findAllByOrderByUpdatedAtDesc(Pageable pageable);

    @Query("select p from Permission p where p.id in ?1")
    List<Permission> findByIds(List<Long> ids);

    Permission findByName(String name);

    Boolean existsByMenuAndMethod(Menu menu, PermissionMethod method);

    Boolean existsByMenuAndMethodAndIdNot(Menu menu, PermissionMethod method, Long id);
}
