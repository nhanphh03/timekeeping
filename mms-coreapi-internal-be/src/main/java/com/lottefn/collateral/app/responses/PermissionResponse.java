package com.lottefn.collateral.app.responses;

import lombok.Data;

@Data
public class PermissionResponse {
    private Long id;
    private String name;
    private String menu;
    private String method;
    private String description;
}
