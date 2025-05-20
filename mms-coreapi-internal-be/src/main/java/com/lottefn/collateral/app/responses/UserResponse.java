package com.lottefn.collateral.app.responses;

import lombok.Data;

@Data
public class UserResponse {
    private long id;

    private String username;

    private String email;

    private String name;

    private String status;

    private RoleResponse role;
}
