package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.ModalidadOferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadOfertaRepository extends JpaRepository<ModalidadOferta, Integer> {
}