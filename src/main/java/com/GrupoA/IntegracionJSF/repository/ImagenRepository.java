package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Integer> {
}