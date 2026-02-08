package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.ValidacionDocumentacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidacionDocumentacionRepository extends JpaRepository<ValidacionDocumentacion, Integer> {
}