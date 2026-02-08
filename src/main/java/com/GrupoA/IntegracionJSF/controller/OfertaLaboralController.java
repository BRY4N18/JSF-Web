package com.example.demo.controller;

import com.example.demo.service.Impl.OfertaLaboralServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ofertas")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class OfertaLaboralController {

    @Autowired
    private OfertaLaboralServiceImpl service;

    @GetMapping("/mayor-salario/{idEmpresa}")
    public ResponseEntity<?> obtenerMayorSalario(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(service.verMejorOferta(idEmpresa));
    }
}