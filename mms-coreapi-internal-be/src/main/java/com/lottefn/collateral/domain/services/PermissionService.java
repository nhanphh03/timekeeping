package com.lottefn.collateral.domain.services;

import com.lottefn.collateral.app.responses.MenuPermissionResponse;
import com.lottefn.collateral.domain.entities.Permission;

import java.util.List;

public interface PermissionService extends BaseService<Permission, Long> {
    List<MenuPermissionResponse> getAll();
}
