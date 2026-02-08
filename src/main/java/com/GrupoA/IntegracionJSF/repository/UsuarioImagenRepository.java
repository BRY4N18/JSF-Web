package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.UsuarioImagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioImagenRepository extends JpaRepository<UsuarioImagen, Integer> {
}