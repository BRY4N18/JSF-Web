package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import com.example.demo.model.UsuarioEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository
public interface EmpresaRepository extends JpaRepository<UsuarioEmpresa, Long> {
    // Busca la empresa vinculada al id_usuario (Relación @OneToOne o @ManyToOne)
    Optional<UsuarioEmpresa> findByUsuarioIdUsuario(Long idUsuario);
}