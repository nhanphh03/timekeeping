package com.lottefn.collateral.app.requests.role;

import com.lottefn.collateral.app.requests.DTO;
import com.lottefn.collateral.domain.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RoleRequest implements DTO<Role> {

    @NotBlank
    @NotNull
    private String name;
    private String description;
    private List<Long> permissionIds;
}
