package com.lottefn.collateral.domain.annotations.enum_convert;

import com.lottefn.collateral.domain.enums.Menu;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class MenuConverter extends EnumAttributeConverter<Menu> {

    public MenuConverter() {
        super(Menu.class);
    }
}