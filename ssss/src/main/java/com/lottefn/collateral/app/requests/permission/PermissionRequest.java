package com.lottefn.collateral.app.requests.permission;

import com.lottefn.collateral.app.requests.DTO;
import com.lottefn.collateral.domain.entities.Permission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionRequest implements DTO<Permission> {
    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String menu;

    @NotBlank
    @NotNull
    private String method;
    private String description;
}
