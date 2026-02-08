package com.example.demo.controller;
import com.example.demo.model.OfertaLaboral;
import com.example.demo.repository.OfertaLaboralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class OfertaController {

    @Autowired
    private OfertaLaboralRepository ofertaLaboralRepository;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarOferta(@RequestBody OfertaLaboral nuevaOferta) {
        try {
            //  Guardamos la oferta que viene de del Usuario
            OfertaLaboral guardada = ofertaLaboralRepository.save(nuevaOferta);
            return ResponseEntity.ok(guardada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al insertar: " + e.getMessage());
        }
    }

    // 1. Método para VER las ofertas de una empresa específica
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<?> listarPorEmpresa(@PathVariable Integer idEmpresa) {
        try {
            // Buscamos en la BD usando el método que definimos en el repositorio
            List<OfertaLaboral> ofertas = ofertaLaboralRepository.findByEmpresaIdEmpresa(idEmpresa);
            return ResponseEntity.ok(ofertas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al consultar: " + e.getMessage());
        }
    }

    // 2. Método para ELIMINAR una oferta
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOferta(@PathVariable Long id) {
        try {
            ofertaLaboralRepository.deleteById(id);
            // Devuelve un 204 No Content, que es lo ideal para un DELETE
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // 3. Método para ACTUALIZAR una oferta existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarOferta(@PathVariable Long id, @RequestBody OfertaLaboral datosActualizados) {
        return ofertaLaboralRepository.findById(id).map(oferta -> {
            // Actualizamos los campos básicos
            oferta.setTitulo(datosActualizados.getTitulo());
            oferta.setDescripcion(datosActualizados.getDescripcion());
            oferta.setSalarioPromedio(datosActualizados.getSalarioPromedio());
            oferta.setFechaInicio(datosActualizados.getFechaInicio());
            oferta.setFechaCierre(datosActualizados.getFechaCierre());
            oferta.setEstadoOferta(datosActualizados.getEstadoOferta());

            // Actualizamos las relaciones (Categoría, Modalidad, etc.)
            // JPA se encarga de mapear estos objetos si vienen con su ID
            oferta.setCategoria(datosActualizados.getCategoria());
            oferta.setModalidad(datosActualizados.getModalidad());
            oferta.setCiudad(datosActualizados.getCiudad());
            oferta.setJornada(datosActualizados.getJornada());

            // Guardamos los cambios en PostgreSQL
            OfertaLaboral actualizada = ofertaLaboralRepository.save(oferta);
            return ResponseEntity.ok(actualizada);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}