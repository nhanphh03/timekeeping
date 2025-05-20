package com.lottefn.collateral.domain.services.impl;

import com.lottefn.collateral.app.requests.DTO;
import com.lottefn.collateral.app.requests.FilterDTO;
import com.lottefn.collateral.app.requests.role.RoleRequest;
import com.lottefn.collateral.domain.entities.Permission;
import com.lottefn.collateral.domain.entities.Role;
import com.lottefn.collateral.domain.exceptions.BusinessException;
import com.lottefn.collateral.domain.repositories.PermissionRepository;
import com.lottefn.collateral.domain.repositories.RoleRepository;
import com.lottefn.collateral.domain.services.BaseAbtractService;
import com.lottefn.collateral.domain.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lottefn.collateral.domain.exceptions.ErrorMessage.ROLE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseAbtractService implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public Page<Role> findAll(FilterDTO dto, Pageable pageable) {
        return roleRepository.findAllByOrderByUpdatedAtDesc(pageable);
    }

    @Override
    public Role findById(HttpServletRequest request, Long id) {
        return null;
    }

    @Override
    public Role create(HttpServletRequest request, DTO dto) {
        RoleRequest roleRequest = modelMapper.map(dto, RoleRequest.class);
        Role role = modelMapper.map(roleRequest, Role.class);
        List<Permission> permissionList = permissionRepository.findByIds(roleRequest.getPermissionIds());
        role.setPermissions(permissionList);
        return roleRepository.save(role);
    }

    @Override
    public Role update(HttpServletRequest request, Long id, DTO dto) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ROLE_NOT_FOUND));
        RoleRequest roleRequest = modelMapper.map(dto, RoleRequest.class);
        BeanUtils.copyProperties(roleRequest, role);
        List<Permission> permissionList = permissionRepository.findByIds(roleRequest.getPermissionIds());
        role.setPermissions(permissionList);
        return roleRepository.save(role);
    }

    @Override
    public boolean delete(HttpServletRequest request, Long id) {
        roleRepository.deleteById(id);
        return true;
    }

}
