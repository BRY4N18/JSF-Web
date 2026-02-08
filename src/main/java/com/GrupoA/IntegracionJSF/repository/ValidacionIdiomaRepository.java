package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.ValidacionIdioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidacionIdiomaRepository extends JpaRepository<ValidacionIdioma, Integer> {
}