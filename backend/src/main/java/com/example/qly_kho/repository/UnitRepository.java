package com.example.qly_kho.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qly_kho.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    boolean existsByName(String name);

    Optional<Unit> findByName(String name);
}
