package com.example.demo.repository;

import com.example.demo.model.CategoriaOferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaOfertaRepository extends JpaRepository<CategoriaOferta, Integer> {
    // Al heredar de JpaRepository, ya tienes findAll(), findById(), save(), etc.
}