package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.Cursos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursosRepository extends JpaRepository<Cursos, Integer> {
}