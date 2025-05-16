package com.lottefn.collateral.domain.annotations.enum_convert;

import com.lottefn.collateral.domain.enums.PermissionMethod;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PermissionMethodConverter extends EnumAttributeConverter<PermissionMethod> {

    public PermissionMethodConverter() {
        super(PermissionMethod.class);
    }
}