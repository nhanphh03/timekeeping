package com.lottefn.collateral.domain.services.impl;

import com.lottefn.collateral.app.responses.MenuResponse;
import com.lottefn.collateral.domain.enums.Menu;
import com.lottefn.collateral.domain.enums.PermissionMethod;
import com.lottefn.collateral.domain.services.BaseAbtractService;
import com.lottefn.collateral.domain.services.MetadataService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetadataServiceImpl extends BaseAbtractService implements MetadataService {

    @Override
    public List<MenuResponse> getAllMenu() {
        return Arrays.stream(Menu.values()).map(item -> modelMapper.map(item, MenuResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<PermissionMethod> getAllPermissionMethod() {
        return Arrays.asList(PermissionMethod.values());
    }
}
