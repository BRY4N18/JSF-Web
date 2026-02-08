package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}