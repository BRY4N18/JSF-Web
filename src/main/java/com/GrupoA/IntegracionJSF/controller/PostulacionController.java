package com.example.demo.controller;

import com.example.demo.model.Postulacion;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PostulacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/postulaciones")
@CrossOrigin(origins = "*") // Permite la comunicación con SvelteKit
public class PostulacionController {

    @Autowired
    private PostulacionRepository postulacionRepository;

    /**
     * Obtiene los candidatos únicos basándose en el idUsuario de la empresa logueada.
     * El JOIN en el repositorio se encarga de encontrar la empresa del usuario.
     */
    @GetMapping("/empresa/{idUsuarioEmpresa}/postulantes")
    public List<Usuario> getPostulantes(@PathVariable Integer idUsuarioEmpresa) {
        // Usamos el nuevo método con JOIN que atraviesa Usuario -> Empresa -> Oferta -> Postulacion
        return postulacionRepository.findUsuariosByIdUsuarioEmpresa(idUsuarioEmpresa);
    }

    /**
     * Obtiene las ofertas de un candidato específico, filtrando por la empresa
     * asociada al idUsuario logueado actualmente.
     */
    @GetMapping("/usuario/{idUsuarioPostulante}/ofertas")
    public ResponseEntity<?> getOfertasPorPostulante(
            @PathVariable Integer idUsuarioPostulante,
            @RequestParam Integer idUsuarioEmpresa) {

        List<Postulacion> lista = postulacionRepository.findPostulacionesByUsuarioAndIdUsuarioEmpresa(
                idUsuarioPostulante,
                idUsuarioEmpresa
        );

        // Mapeamos a un formato JSON limpio para las cartas de Svelte
        var respuesta = lista.stream()
                .filter(p -> p.getOferta() != null) // <--- ESTO EVITA EL NULL POINTER EXCEPTION
                .map(p -> Map.of(
                        "idPostulacion", p.getIdPostulacion(),
                        "titulo", p.getOferta().getTitulo(),
                        "descripcion", p.getOferta().getDescripcion(),
                        "fechaPostulacion", p.getFechaPostulacion() != null ? p.getFechaPostulacion().toString() : ""
                )).collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Actualiza el estado de la postulación en PostgreSQL (CONTRATADO/RECHAZADO).
     */
    @PutMapping("/actualizar-estado/{id}")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {

        return postulacionRepository.findById(id).map(p -> {
            p.setEstadoPostulacion(body.get("estado"));
            postulacionRepository.save(p);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}