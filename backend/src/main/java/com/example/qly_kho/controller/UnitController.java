package com.example.qly_kho.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.qly_kho.dto.response.unit.UnitResponse;
import com.example.qly_kho.service.application.impl.UnitAppSerivceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitAppSerivceImpl unitAppSerivceImpl;

    @RequestMapping("/list")
    public ResponseEntity<List<UnitResponse>> getUnitList() {

        return ResponseEntity.ok(unitAppSerivceImpl.getUnitList());
    }
}
