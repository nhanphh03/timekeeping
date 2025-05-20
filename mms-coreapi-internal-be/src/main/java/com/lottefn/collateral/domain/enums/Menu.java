package com.lottefn.collateral.domain.enums;

import com.lottefn.collateral.domain.exceptions.BusinessException;
import com.lottefn.collateral.domain.exceptions.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public enum Menu {
    COLLATERAL("COLLATERAL", "Collateral", "/api/collateral"),
    COLLATERAL_CODE("COLLATERAL_CODE", "Collateral code", "/api/collateral-code"),
    REPORT("REPORT", "Report", "/api/report"),
    USER("USER", "User", "/api/user"),
    ROLE("ROLE", "Role", "/api/role"),
    PERMISSION("PERMISSION", "Permission", "/api/permission")
    ;

    private final String code;

    private final String name;

    private final String url;

    Menu(String code, String name, String url) {
        this.name = name;
        this.code = code;
        this.url = url;
    }

    public static Menu getByCode (String code) {
        return Arrays.stream(Menu.values()).filter(item -> item.code.equals(code))
                .findFirst().orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ErrorMessage.MENU_NOT_FOUND));
    }
}
