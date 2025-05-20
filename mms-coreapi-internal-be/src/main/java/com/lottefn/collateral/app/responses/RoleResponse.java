package com.lottefn.collateral.app.responses;

import lombok.Data;

import java.util.Set;

@Data
public class RoleResponse {
    private Long id;
    private String name;
    private  String description;
    private Set<PermissionResponse> permissions;
}
