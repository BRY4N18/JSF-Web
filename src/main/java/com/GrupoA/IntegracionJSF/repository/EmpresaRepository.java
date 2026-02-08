package com.GrupoA.IntegracionJSF.repository;

import org.springframework.stereotype.Repository;
import com.GrupoA.IntegracionJSF.model.UsuarioEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository
public interface EmpresaRepository extends JpaRepository<UsuarioEmpresa, Long> {
    // Busca la empresa vinculada al id_usuario (Relación @OneToOne o @ManyToOne)
    Optional<UsuarioEmpresa> findByUsuarioIdUsuario(Long idUsuario);
}