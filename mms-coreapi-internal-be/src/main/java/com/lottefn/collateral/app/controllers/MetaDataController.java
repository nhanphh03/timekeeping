package com.lottefn.collateral.app.controllers;


import com.lottefn.collateral.app.responses.MenuResponse;
import com.lottefn.collateral.domain.enums.PermissionMethod;
import com.lottefn.collateral.domain.services.MetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/metadata")
@RestController
@RequiredArgsConstructor
public class MetaDataController {

    private final MetadataService metadataService;

    @GetMapping("permission-menu")
    PermissionMethodResponse getAllMenu() {
        return new PermissionMethodResponse(metadataService.getAllMenu(), metadataService.getAllPermissionMethod());
    }

    record PermissionMethodResponse (List<MenuResponse> menus, List<PermissionMethod> permissionMethod){}

}


