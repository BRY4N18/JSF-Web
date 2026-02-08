package com.GrupoA.IntegracionJSF.controller;

import com.GrupoA.IntegracionJSF.dto.OfertaDto;
import com.GrupoA.IntegracionJSF.model.*;
import com.GrupoA.IntegracionJSF.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Bean gestionado para la vista de Ofertas Laborales.
 * Se usa @Named para JSF y @Component para la integración con Spring.
 */
@Named("ofertaBean")
@Component
@ViewScoped
public class OfertaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- INYECCIÓN DE REPOSITORIOS ---
    @Autowired private OfertaLaboralRepository ofertaRepository;
    @Autowired private CategoriaOfertaRepository categoriaRepository;
    @Autowired private ModalidadOfertaRepository modalidadRepository;
    @Autowired private JornadaOfertaRepository jornadaRepository;
    @Autowired private CiudadRepository ciudadRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    // --- VARIABLES PARA LA VISTA ---
    private OfertaDto ofertaActual;
    private List<OfertaLaboral> listaOfertas;

    // Listas para los Combobox (SelectOneMenu)
    private List<CategoriaOferta> listaCategorias;
    private List<ModalidadOferta> listaModalidades;
    private List<JornadaOferta> listaJornadas;
    private List<Ciudad> listaCiudades;

    @PostConstruct
    public void init() {
        limpiarFormulario();
        cargarCatalogos();
        cargarOfertasPublicadas();
    }

    public void limpiarFormulario() {
        this.ofertaActual = new OfertaDto();
        this.ofertaActual.setFechaInicio(new Date()); // Fecha por defecto
    }

    private void cargarCatalogos() {
        this.listaCategorias = categoriaRepository.findAll();
        this.listaModalidades = modalidadRepository.findAll();
        this.listaJornadas = jornadaRepository.findAll();
        this.listaCiudades = ciudadRepository.findAll();
    }

    public void cargarOfertasPublicadas() {
        // En una Bolsa de Empleo real, aquí filtrarías por el ID de la empresa en sesión
        this.listaOfertas = ofertaRepository.findAll();
    }

    public void guardarOferta() {
        try {
            OfertaLaboral entidad;

            // --- 1. ¿Es Edición o Creación? ---
            if (ofertaActual.getIdOferta() != null) {
                entidad = ofertaRepository.findById(Long.valueOf(ofertaActual.getIdOferta()))
                        .orElse(new OfertaLaboral());
            } else {
                entidad = new OfertaLaboral();
            }

            // --- 2. Mapeo de datos básicos ---
            entidad.setTitulo(ofertaActual.getTitulo());
            entidad.setDescripcion(ofertaActual.getDescripcion());
            entidad.setSalarioPromedio(ofertaActual.getSalarioPromedio());
            entidad.setEstadoOferta("Activa");

            // --- 3. Conversión de Fechas (java.util.Date -> java.time.LocalDate) ---
            if (ofertaActual.getFechaInicio() != null) {
                entidad.setFechaInicio(ofertaActual.getFechaInicio().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());
            }
            if (ofertaActual.getFechaCierre() != null) {
                entidad.setFechaCierre(ofertaActual.getFechaCierre().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());
            }

            // --- 4. Asignar Relaciones (Catálogos) ---
            if (ofertaActual.getIdCategoria() != null)
                entidad.setCategoria(categoriaRepository.findById(ofertaActual.getIdCategoria()).orElse(null));

            if (ofertaActual.getIdModalidad() != null)
                entidad.setModalidad(modalidadRepository.findById(ofertaActual.getIdModalidad()).orElse(null));

            if (ofertaActual.getIdJornada() != null)
                entidad.setJornada(jornadaRepository.findById(ofertaActual.getIdJornada()).orElse(null));

            if (ofertaActual.getIdCiudad() != null)
                entidad.setCiudad(ciudadRepository.findById(ofertaActual.getIdCiudad()).orElse(null));

            // --- 5. Asignar Empresa (Desde la Sesión de AuthController) ---
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Long idUsuario = (Long) session.getAttribute("session_id_usuario");

            if (idUsuario != null) {
                // Buscamos el usuario de forma tradicional
                Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

                if (usuarioOpt.isPresent()) {
                    UsuarioEmpresa emp = new UsuarioEmpresa();
                    emp.setUsuario(usuarioOpt.get()); // Aquí usamos .get() directamente
                    entidad.setEmpresa(emp); // 'entidad' ahora puede ser usada sin ser final
                }
            }

            // --- 6. Persistencia ---
            ofertaRepository.save(entidad);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmación", "La oferta se ha guardado con éxito."));

            limpiarFormulario();
            cargarOfertasPublicadas();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error crítico", "Detalle: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void cargarParaEditar(OfertaLaboral oferta) {
        this.ofertaActual = new OfertaDto();
        this.ofertaActual.setIdOferta(oferta.getIdOferta());
        this.ofertaActual.setTitulo(oferta.getTitulo());
        this.ofertaActual.setDescripcion(oferta.getDescripcion());
        this.ofertaActual.setSalarioPromedio(oferta.getSalarioPromedio());

        // Conversión inversa: LocalDate -> Date para el calendario de JSF
        if (oferta.getFechaInicio() != null)
            this.ofertaActual.setFechaInicio(Date.from(oferta.getFechaInicio()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));

        if (oferta.getFechaCierre() != null)
            this.ofertaActual.setFechaCierre(Date.from(oferta.getFechaCierre()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));

        // Asignación de IDs para los SelectOneMenu
        if (oferta.getCategoria() != null) this.ofertaActual.setIdCategoria(oferta.getCategoria().getIdCategoria());
        if (oferta.getModalidad() != null) this.ofertaActual.setIdModalidad(oferta.getModalidad().getIdModalidad());
        if (oferta.getJornada() != null) this.ofertaActual.setIdJornada(oferta.getJornada().getIdJornada());
        if (oferta.getCiudad() != null) this.ofertaActual.setIdCiudad(oferta.getCiudad().getIdCiudad());
    }

    public void eliminarOferta(OfertaLaboral oferta) {
        try {
            ofertaRepository.delete(oferta);
            cargarOfertasPublicadas();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminado", "La oferta ha sido retirada."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la oferta."));
        }
    }

    // --- GETTERS Y SETTERS ---
    public OfertaDto getOfertaActual() { return ofertaActual; }
    public void setOfertaActual(OfertaDto ofertaActual) { this.ofertaActual = ofertaActual; }
    public List<OfertaLaboral> getListaOfertas() { return listaOfertas; }
    public void setListaOfertas(List<OfertaLaboral> listaOfertas) { this.listaOfertas = listaOfertas; }
    public List<CategoriaOferta> getListaCategorias() { return listaCategorias; }
    public void setListaCategorias(List<CategoriaOferta> listaCategorias) { this.listaCategorias = listaCategorias; }
    public List<ModalidadOferta> getListaModalidades() { return listaModalidades; }
    public void setListaModalidades(List<ModalidadOferta> listaModalidades) { this.listaModalidades = listaModalidades; }
    public List<JornadaOferta> getListaJornadas() { return listaJornadas; }
    public void setListaJornadas(List<JornadaOferta> listaJornadas) { this.listaJornadas = listaJornadas; }
    public List<Ciudad> getListaCiudades() { return listaCiudades; }
    public void setListaCiudades(List<Ciudad> listaCiudades) { this.listaCiudades = listaCiudades; }
}