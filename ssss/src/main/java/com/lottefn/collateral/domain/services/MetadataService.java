package com.lottefn.collateral.domain.services;

import com.lottefn.collateral.app.responses.MenuResponse;
import com.lottefn.collateral.domain.enums.PermissionMethod;

import java.util.List;

public interface MetadataService {
    List<MenuResponse> getAllMenu();

    List<PermissionMethod> getAllPermissionMethod();
}
