package com.lottefn.collateral.app.controllers;

import com.lottefn.collateral.app.requests.user.UserFilterDTO;
import com.lottefn.collateral.app.requests.user.UserRequest;
import com.lottefn.collateral.app.responses.PageResponse;
import com.lottefn.collateral.app.responses.UserResponse;
import com.lottefn.collateral.domain.entities.User;
import com.lottefn.collateral.domain.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
public class UserController extends BaseController<User, Long, UserResponse, UserFilterDTO>{

    protected UserController(UserService userService) {
        super(UserResponse.class, UserFilterDTO.class, userService);
    }

    @GetMapping()
    PageResponse<UserResponse> findAll(UserFilterDTO filterDTO, Pageable pageable) {
        return super.findAll(filterDTO, pageable);
    }

    @PostMapping()
    UserResponse create(HttpServletRequest request, @Valid @RequestBody UserRequest req) {
        return super.create(request, req);
    }

    @DeleteMapping("/{id}")
    boolean deleteUser(HttpServletRequest request, @PathVariable Long id) {
        return super.delete(request, id);
    }

    @PatchMapping("/{id}")
    UserResponse update(HttpServletRequest request, @PathVariable Long id, @Valid @RequestBody UserRequest req) {
        return super.update(request, id, req);
    }

}
