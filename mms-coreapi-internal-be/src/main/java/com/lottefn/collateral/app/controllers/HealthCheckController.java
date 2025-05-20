package com.lottefn.collateral.app.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthCheckController {
    @GetMapping("healthy")
    public ResponseEntity<String> healthCheck() {

        return ResponseEntity.ok("Collateral Core is running");
    }
}
