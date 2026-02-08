package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.ValidacionExpLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidacionExpLaboralRepository extends JpaRepository<ValidacionExpLaboral, Integer> {
}