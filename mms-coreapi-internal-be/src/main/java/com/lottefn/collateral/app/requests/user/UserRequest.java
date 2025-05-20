package com.lottefn.collateral.app.requests.user;

import com.lottefn.collateral.app.requests.DTO;
import com.lottefn.collateral.domain.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest implements DTO<Role> {
    @NotNull
    private String name;
    @NotNull
    private String username;
    @Email
    private String email;
    @NotNull
    private Long role;
    private String status;
    @Size(min = 8, max = 20)
    private String password;
    private String description;
}
