package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.ValidacionCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidacionCursoRepository extends JpaRepository<ValidacionCurso, Integer> {
}