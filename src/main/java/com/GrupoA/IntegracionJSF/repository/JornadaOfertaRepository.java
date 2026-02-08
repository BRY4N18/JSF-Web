package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.JornadaOferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaOfertaRepository extends JpaRepository<JornadaOferta, Integer> {
}