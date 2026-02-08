package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.Postulacion;
import com.GrupoA.IntegracionJSF.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Integer> {

    // 1. Obtener postulantes únicos filtrando por el ID del USUARIO de la empresa
    @Query("SELECT DISTINCT p.usuario FROM Postulacion p " +
            "WHERE p.oferta.empresa.usuario.idUsuario = :idUsuario " +
            "AND p.estadoPostulacion = 'POSTULADO'")
    List<Usuario> findUsuariosByIdUsuarioEmpresa(@Param("idUsuario") Integer idUsuario);

    // PostulacionRepository.java
    @Query("SELECT p FROM Postulacion p WHERE p.usuario.idUsuario = :idUsuarioPostulante " +
            "AND p.oferta.empresa.usuario.idUsuario = :idUsuarioEmpresa " +
            "AND p.estadoPostulacion = 'POSTULADO'")
    List<Postulacion> findPostulacionesByUsuarioAndIdUsuarioEmpresa(
            @Param("idUsuarioPostulante") Integer idUsuarioPostulante,
            @Param("idUsuarioEmpresa") Integer idUsuarioEmpresa
    );
}