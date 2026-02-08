package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.OfertasFavoritas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertasFavoritasRepository extends JpaRepository<OfertasFavoritas, Integer> {
}