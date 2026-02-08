package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.ExpLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpLaboralRepository extends JpaRepository<ExpLaboral, Integer> {
}