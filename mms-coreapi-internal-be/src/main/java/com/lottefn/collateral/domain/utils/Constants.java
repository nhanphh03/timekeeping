package com.lottefn.collateral.domain.utils;

import java.util.List;

public class Constants {
    public static final String COOKIE_KEY = "access_token";

    public static final List<String> AUTH_WHITE_LIST = List.of(
            "/api/permission/parent-child", "/api/metadata/permission-menu", "/api/auth/info");
}
