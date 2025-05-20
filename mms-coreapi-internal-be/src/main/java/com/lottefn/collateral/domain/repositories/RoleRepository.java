package com.lottefn.collateral.domain.repositories;

import com.lottefn.collateral.domain.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Boolean existsByName(String name);

    Page<Role> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
