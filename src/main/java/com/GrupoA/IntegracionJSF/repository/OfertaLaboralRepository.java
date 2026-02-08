package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.OfertaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List; // 👈 ¡INDISPENSABLE! Para que no salga rojo
import java.util.Map;

@Repository
public interface OfertaLaboralRepository extends JpaRepository<OfertaLaboral, Long> {

    List<OfertaLaboral> findByEmpresaIdEmpresa(Integer idEmpresa);

    @Procedure(procedureName = "obtener_oferta_mayor_salario")
    Map<String, Object> obtenerOfertaMayorSalario(@Param("pid_empresa") Integer idEmpresa);

}