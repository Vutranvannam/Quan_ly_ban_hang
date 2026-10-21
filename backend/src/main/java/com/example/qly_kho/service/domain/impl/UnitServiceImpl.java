package com.example.qly_kho.service.domain.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.Unit;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.repository.UnitRepository;
import com.example.qly_kho.service.domain.UnitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UnitServiceImpl implements UnitService {
    
    private final UnitRepository unitRepository;

    @Override
    public void existsByName(String name) {

        if (unitRepository.existsByName(name)) {
            throw new DuplicateException(
                String.format("Unit with name: %s already exists in UnitService", name)
            );
        }
    }

    @Override
    public Unit findOrCreateByName(String name) {
        
        return unitRepository.findByName(name)
            .orElseGet(() -> {
                Unit unit = Unit.createUnit(name);
                return unitRepository.save(unit);
            });
    }

    @Override
    public Unit saveUnit(Unit unit) {
        existsByName(unit.getName());

        try {
            return unitRepository.save(unit);
        } catch (Exception e) {
            if (e.getMessage().contains("uk_units_name")) {
                throw new DuplicateException("Username already exists in UserService");
            }
            throw new DuplicateException("Duplicate data");
        }
    }

    @Override
    public List<Unit> getAllUnits() {
        
        return unitRepository.findAll();
    }
}
