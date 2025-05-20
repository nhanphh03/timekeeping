package com.lottefn.collateral.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PermissionMethod {
    LIST("LIST", HttpMethod.GET, ""),
    CREATE("CREATE", HttpMethod.POST, ""),
    UPDATE("UPDATE", HttpMethod.PATCH, ""),
    DELETE("DELETE", HttpMethod.DELETE, "")
    ;

//    @JsonProperty
    private final String name;

//    @JsonProperty
    private final HttpMethod method;

//    @JsonProperty
    private final String path;

    PermissionMethod(String name, HttpMethod method, String path) {
        this.name = name;
        this.method = method;
        this.path = path;
    }
}
