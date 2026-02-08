package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.SeguridadDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguridadDbRepository extends JpaRepository<SeguridadDb, Integer> {
}