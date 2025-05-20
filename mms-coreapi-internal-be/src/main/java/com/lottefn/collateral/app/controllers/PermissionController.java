package com.lottefn.collateral.app.controllers;

import com.lottefn.collateral.app.requests.permission.PermissionFilterDTO;
import com.lottefn.collateral.app.requests.permission.PermissionRequest;
import com.lottefn.collateral.app.responses.MenuPermissionResponse;
import com.lottefn.collateral.app.responses.PageResponse;
import com.lottefn.collateral.app.responses.PermissionResponse;
import com.lottefn.collateral.domain.entities.Permission;
import com.lottefn.collateral.domain.services.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/permission")
@RestController
public class PermissionController extends BaseController<Permission, Long, PermissionResponse, PermissionFilterDTO> {

    final PermissionService permissionService;

    protected PermissionController(PermissionService service) {
        super(PermissionResponse.class, PermissionFilterDTO.class, service);
        this.permissionService = service;
    }

    @PostMapping
    PermissionResponse create(HttpServletRequest request, @Valid @RequestBody PermissionRequest dto) {
        return super.create(request, dto);
    }

    @GetMapping
    PageResponse<PermissionResponse> findAll(PermissionFilterDTO dto, Pageable pageable) {
        return super.findAll(dto, pageable);
    }

    @PatchMapping("/{id}")
    PermissionResponse update(HttpServletRequest request, @PathVariable Long id, @Valid @RequestBody PermissionRequest dto) {
        return super.update(request, id, dto);
    }

    @DeleteMapping("/{id}")
    boolean delete(HttpServletRequest request, @PathVariable Long id) {
        return super.delete(request, id);
    }

    @GetMapping("parent-child")
    List<MenuPermissionResponse> getAll() {
        return permissionService.getAll();
    }

}
