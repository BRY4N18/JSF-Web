package com.GrupoA.IntegracionJSF.repository;

import com.GrupoA.IntegracionJSF.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
}