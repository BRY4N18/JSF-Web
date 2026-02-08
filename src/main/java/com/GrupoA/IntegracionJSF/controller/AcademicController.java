package com.GrupoA.IntegracionJSF.controller;

import com.GrupoA.IntegracionJSF.model.Facultad;
import com.GrupoA.IntegracionJSF.model.Carrera;
import com.GrupoA.IntegracionJSF.model.Idioma;
import com.GrupoA.IntegracionJSF.model.JornadaOferta;
import com.GrupoA.IntegracionJSF.model.ModalidadOferta;
import com.GrupoA.IntegracionJSF.model.CategoriaOferta;
import com.GrupoA.IntegracionJSF.model.Ciudad;
import org.springframework.http.ResponseEntity;

import com.GrupoA.IntegracionJSF.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academico")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST}) // 👈 Permite que Angular (puerto 4200) se conecte
public class AcademicController {

    @Autowired
    private FacultadRepository facultadRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private ModalidadOfertaRepository modalidadRepository;

    @Autowired
    private CategoriaOfertaRepository categoriaRepository;

    @Autowired
    private JornadaOfertaRepository jornadaRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private  EmpresaRepository empresaRepository;
    //Devuelve la lista de todas las facultades registras en mi BD
    @GetMapping("/facultades")
    public List<Facultad> listarFacultades() {
        return facultadRepository.findAll();
    }
    //Devuelve la lista de todas las carreras registras en mi BD
    @GetMapping("/carreras/{idFacultad}")
    public List<Carrera> listarCarrerasPorFacultad(@PathVariable Integer idFacultad) {
        return carreraRepository.findByFacultadIdFacultad(idFacultad);
    }

    @GetMapping("/idiomas")
    public List<Idioma> listarIdiomas() {
        return idiomaRepository.findAll();
    }


    //Para las ofertass
    // Endpoint para obtener las modalidades (Presencial, Remoto, etc.)
    @GetMapping("/modalidades")
    public List<ModalidadOferta> listarModalidades() {
        return modalidadRepository.findAll();
    }

    // Endpoint para obtener las categorías (IT, Marketing, Salud, etc.)
    @GetMapping("/categorias")
    public List<CategoriaOferta> listarCategorias() {
        return categoriaRepository.findAll();
    }

    // Endpoint para obtener las jornadas (Tiempo Completo, Medio Tiempo)
    @GetMapping("/jornadas")
    public List<JornadaOferta> listarJornadas() {
        return jornadaRepository.findAll();
    }

    @GetMapping("/ciudades")
    public List<Ciudad> listarCiudades() {
        return ciudadRepository.findAll();
    }

    @GetMapping("/mi-empresa/{idUsuario}")
    public ResponseEntity<?> obtenerMiEmpresa(@PathVariable Long idUsuario) {
        return empresaRepository.findByUsuarioIdUsuario(idUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}