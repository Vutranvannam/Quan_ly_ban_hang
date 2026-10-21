package com.example.qly_kho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.qly_kho.dto.response.unit.UnitResponse;
import com.example.qly_kho.entity.Unit;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    @Mapping(source = "id", target = "unitId")
    UnitResponse toUnitResponse(Unit unit);
}
