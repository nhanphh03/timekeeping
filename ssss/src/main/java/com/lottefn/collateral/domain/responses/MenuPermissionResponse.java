package com.lottefn.collateral.app.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MenuPermissionResponse {
    private MenuResponse menu;
    private List<PermissionResponse> permissions;
}
