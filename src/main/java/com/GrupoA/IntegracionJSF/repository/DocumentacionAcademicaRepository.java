package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.DocumentacionAcademica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentacionAcademicaRepository extends JpaRepository<DocumentacionAcademica, Integer> {
}