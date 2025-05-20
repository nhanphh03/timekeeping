package com.lottefn.collateral.app.controllers;

import com.lottefn.collateral.app.requests.role.RoleFilterDTO;
import com.lottefn.collateral.app.requests.role.RoleRequest;
import com.lottefn.collateral.app.responses.PageResponse;
import com.lottefn.collateral.app.responses.RoleResponse;
import com.lottefn.collateral.domain.entities.Role;
import com.lottefn.collateral.domain.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/role")
@RestController
public class RoleController extends BaseController<Role, Long, RoleResponse, RoleFilterDTO> {

    protected RoleController(RoleService roleService) {
        super(RoleResponse.class, RoleFilterDTO.class, roleService);
    }

    @GetMapping()
    PageResponse<RoleResponse> findAll(RoleFilterDTO filterDTO, Pageable pageable) {
        return super.findAll(filterDTO, pageable);
    }

    @PostMapping()
    RoleResponse create(HttpServletRequest request, @Valid @RequestBody RoleRequest req) {
        return super.create(request, req);
    }

    @PatchMapping("/{id}")
    RoleResponse update(HttpServletRequest request, @PathVariable Long id, @Valid @RequestBody RoleRequest req) {
        return super.update(request, id, req);
    }

}
