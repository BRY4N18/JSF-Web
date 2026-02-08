package com.GrupoA.IntegracionJSF.controller;

// Imports de Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// Imports de Java Util
import java.util.List;

// Imports de tus clases locales (Ajusta el paquete si es necesario)
import com.GrupoA.IntegracionJSF.model.Provincia;
import com.GrupoA.IntegracionJSF.model.Ciudad;
import com.GrupoA.IntegracionJSF.repository.ProvinciaRepository;
import com.GrupoA.IntegracionJSF.repository.CiudadRepository;

@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST}) // Permite la conexión con tu frontend en Angular
public class UbicacionController {

    @Autowired 
    private ProvinciaRepository provinciaRepo;

    @Autowired 
    private CiudadRepository ciudadRepo;

    @GetMapping("/provincias")
    public List<Provincia> listarProvincias() {
        return provinciaRepo.findAll();
    }

    @GetMapping("/ciudades/{idProvincia}")
    public List<Ciudad> listarCiudadesPorProvincia(@PathVariable Integer idProvincia) {
    // Debe coincidir con el repositorio: findByProvincia_IdProvincia
    return ciudadRepo.findByProvincia_IdProvincia(idProvincia);
    }
}