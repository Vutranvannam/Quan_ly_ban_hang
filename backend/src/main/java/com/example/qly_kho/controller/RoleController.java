package com.example.qly_kho.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.qly_kho.dto.response.role.RoleResponse;
import com.example.qly_kho.service.application.RoleAppService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleAppService roleApplicationService;

    @GetMapping("/list")
    public ResponseEntity<List<RoleResponse>> getRoleList() {
        return ResponseEntity.ok(roleApplicationService.getRoleList());
    }
}
