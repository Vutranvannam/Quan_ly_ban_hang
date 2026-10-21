package com.example.qly_kho.service.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.dto.response.unit.UnitResponse;
import com.example.qly_kho.entity.Unit;
import com.example.qly_kho.mapper.UnitMapper;
import com.example.qly_kho.service.application.UnitAppService;
import com.example.qly_kho.service.domain.UnitService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitAppSerivceImpl implements UnitAppService {
    
    private final UnitService unitService;
    private final UnitMapper unitMapper;
    
    @Override
    public List<UnitResponse> getUnitList() {

        List<Unit> units = unitService.getAllUnits();

        return units.stream()
                        .map(
                            unit -> unitMapper.toUnitResponse(unit)
                        )
                            .toList();
    }
    
}
