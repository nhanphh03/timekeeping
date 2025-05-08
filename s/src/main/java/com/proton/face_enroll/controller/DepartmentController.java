package com.proton.face_enroll.controller;

import com.proton.face_enroll.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/groups"})
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping({"/get-all"})
    public ResponseEntity<?> getAll(@RequestHeader(value = "Authorization") String authorization) {
        if (authorization == null || authorization.isEmpty()) {
            return ResponseEntity.badRequest().body("Authorization is required");
        } else if (!authorization.equals("dwKddrv9HhHMU0ylpsxpuoZPMkr6KaxDWORJPmss")) {
            return ResponseEntity.badRequest().body("Authorization is invalid");
        } else return ResponseEntity.ok(this.departmentService.findAll());
    }

}
