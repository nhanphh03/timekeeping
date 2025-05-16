package com.lottefn.collateral.domain.services.impl;

import com.lottefn.collateral.app.requests.DTO;
import com.lottefn.collateral.app.requests.FilterDTO;
import com.lottefn.collateral.app.requests.permission.PermissionRequest;
import com.lottefn.collateral.app.responses.MenuPermissionResponse;
import com.lottefn.collateral.app.responses.MenuResponse;
import com.lottefn.collateral.app.responses.PermissionResponse;
import com.lottefn.collateral.domain.entities.Permission;
import com.lottefn.collateral.domain.enums.Menu;
import com.lottefn.collateral.domain.enums.PermissionMethod;
import com.lottefn.collateral.domain.exceptions.BusinessException;
import com.lottefn.collateral.domain.repositories.PermissionRepository;
import com.lottefn.collateral.domain.services.BaseAbtractService;
import com.lottefn.collateral.domain.services.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lottefn.collateral.domain.exceptions.ErrorMessage.PERMISSION_EXIST;
import static com.lottefn.collateral.domain.exceptions.ErrorMessage.PERMISSION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends BaseAbtractService implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Page<Permission> findAll(FilterDTO dto, Pageable pageable) {
        return permissionRepository.findAllByOrderByUpdatedAtDesc(pageable);
    }

    @Override
    public Permission findById(HttpServletRequest request, Long id) {
        return null;
    }

    @Override
    public Permission create(HttpServletRequest request, DTO dto) {
        PermissionRequest pr = modelMapper.map(dto, PermissionRequest.class);
        if (permissionRepository.existsByMenuAndMethod(Menu.valueOf(pr.getMenu()), PermissionMethod.valueOf(pr.getMethod()))) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, PERMISSION_EXIST);
        }
        Permission permission = modelMapper.map(pr, Permission.class);
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(HttpServletRequest request, Long id, DTO dto) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, PERMISSION_NOT_FOUND));
        PermissionRequest pr = modelMapper.map(dto, PermissionRequest.class);
        if (permissionRepository.existsByMenuAndMethodAndIdNot(Menu.valueOf(pr.getMenu()), PermissionMethod.valueOf(pr.getMethod()), id)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, PERMISSION_EXIST);
        }
        BeanUtils.copyProperties(pr, permission);
        return permissionRepository.save(permission);
    }

    @Override
    public boolean delete(HttpServletRequest request, Long id) {
        permissionRepository.deleteById(id);
        return true;
    }

    @Override
    public List<MenuPermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();

        Map<String, MenuPermissionResponse> menuMap = new HashMap<>();
        PermissionResponse temp;
        for (Permission item : permissions) {
            temp = modelMapper.map(item, PermissionResponse.class);
            if (menuMap.get(item.getMenu().getCode()) == null) {
                menuMap.put(item.getMenu().getCode(), new MenuPermissionResponse(modelMapper.map(item.getMenu(), MenuResponse.class), new ArrayList<>(List.of(temp))));
            } else {
                menuMap.get(item.getMenu().getCode()).getPermissions().add(temp);
            }
        }
        permissions.forEach(item -> {

        });
        return menuMap.values().stream().toList();
    }
}
