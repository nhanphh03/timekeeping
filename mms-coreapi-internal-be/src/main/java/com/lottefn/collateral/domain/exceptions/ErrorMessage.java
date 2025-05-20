package com.lottefn.collateral.domain.exceptions;

public enum ErrorMessage {
    USER_NOT_FOUND("User not found"),
    REQUEST_NOT_EMPTY("Request not empty"),
    DUPLICATE_USER("Duplicate user"),
    DUPLICATE_USERNAME("Duplicate username"),
    DUPLICATE_EMAIL("Duplicate email"),
    PASSWORD_INCORRECT("Incorrect password"),
    PERMISSION_NOT_FOUND("Permission not found"),
    ROLE_NOT_FOUND("Role not found"),
    MENU_NOT_FOUND("Menu not found"),
    PERMISSION_EXIST("Permission exist"),
    ;


    public final String val;

    ErrorMessage(String label) {
        val = label;
    }
}
