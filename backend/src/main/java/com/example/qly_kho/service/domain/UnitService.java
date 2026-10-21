package com.example.qly_kho.service.domain;

import java.util.List;

import com.example.qly_kho.entity.Unit;

public interface UnitService {

    void existsByName(String name);

    Unit findOrCreateByName(String name);

    Unit saveUnit(Unit unit);
    
    List<Unit> getAllUnits();
}
