package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {
}