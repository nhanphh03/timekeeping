package com.lottefn.collateral.app.responses;

import lombok.Data;

@Data
public class UserInfoResponse {
    private String name;
    private String email;
    private String username;
    private RoleResponse role;
}
