package com.GrupoA.IntegracionJSF.controller;

import com.GrupoA.IntegracionJSF.dto.OfertaDto;
import com.GrupoA.IntegracionJSF.model.*; // Tus Entidades
import com.GrupoA.IntegracionJSF.repository.*; // Tus Repositorios
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date; // OJO: Asegúrate que sea java.util.Date
import java.util.List;

@Component("ofertaBean") // Nombre para usar en el XHTML
@ViewScoped
public class OfertaBean implements Serializable {

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
    
    // Listas para los Combobox
    private List<CategoriaOferta> listaCategorias;
    private List<ModalidadOferta> listaModalidades;
    private List<JornadaOferta> listaJornadas;
    private List<Ciudad> listaCiudades;

    @PostConstruct
    public void init() {
        // 1. Inicializar formulario vacío y fecha de hoy
        this.ofertaActual = new OfertaDto();
        this.ofertaActual.setFechaInicio(new Date()); 

        // 2. Cargar las listas para los Dropdowns desde la BD
        this.listaCategorias = categoriaRepository.findAll();
        this.listaModalidades = modalidadRepository.findAll();
        this.listaJornadas = jornadaRepository.findAll();
        this.listaCiudades = ciudadRepository.findAll();

        // 3. Cargar las ofertas existentes para la tabla de abajo
        cargarOfertasPublicadas();
    }

    public void cargarOfertasPublicadas() {
        // Aquí podrías filtrar por la empresa logueada si quisieras
        this.listaOfertas = ofertaRepository.findAll();
    }

    public void guardarOferta() {
        try {
            OfertaLaboral entidad = new OfertaLaboral();
            
            // --- 1. ¿Es Edición o Creación? ---
            if (ofertaActual.getIdOferta() != null) {
                entidad = ofertaRepository.findById(Long.valueOf(ofertaActual.getIdOferta())).orElse(new OfertaLaboral());
            }

            // --- 2. Mapeo de datos básicos ---
            entidad.setTitulo(ofertaActual.getTitulo());
            entidad.setDescripcion(ofertaActual.getDescripcion());
            entidad.setSalarioPromedio(ofertaActual.getSalarioPromedio());
            entidad.setEstadoOferta("Activa");

            // --- 3. Conversión de Fechas (Date -> LocalDate) ---
            if (ofertaActual.getFechaInicio() != null) {
                entidad.setFechaInicio(ofertaActual.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            if (ofertaActual.getFechaCierre() != null) {
                entidad.setFechaCierre(ofertaActual.getFechaCierre().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }

            // --- 4. Asignar Relaciones (Buscando por ID) ---
            // Validamos que se haya seleccionado algo
            if(ofertaActual.getIdCategoria() != null)
                entidad.setCategoria(categoriaRepository.findById(ofertaActual.getIdCategoria()).orElse(null));
            
            if(ofertaActual.getIdModalidad() != null)
                entidad.setModalidad(modalidadRepository.findById(ofertaActual.getIdModalidad()).orElse(null));
            
            if(ofertaActual.getIdJornada() != null)
                entidad.setJornada(jornadaRepository.findById(ofertaActual.getIdJornada()).orElse(null));

            if(ofertaActual.getIdCiudad() != null)
                entidad.setCiudad(ciudadRepository.findById(ofertaActual.getIdCiudad()).orElse(null));

            // --- 5. Asignar Empresa (Desde la Sesión) ---
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            Long idUsuario = (Long) session.getAttribute("session_id_usuario");
            
            if (idUsuario != null) {
                // Asumiendo que UsuarioEmpresa hereda o se relaciona con Usuario, 
                // aquí deberías buscar la empresa correcta. Por simplicidad busco una genérica:
                // Lo ideal: entidad.setEmpresa(usuarioRepository.findEmpresaById(idUsuario));
                // Parche temporal si no tienes ese método:
                 UsuarioEmpresa emp = new UsuarioEmpresa(); 
                 emp.setUsuario(idUsuario);
                 entidad.setEmpresa(emp);
            }

            // --- 6. Guardar ---
            ofertaRepository.save(entidad);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Oferta Guardada"));
            
            // Limpiar formulario y recargar lista
            this.ofertaActual = new OfertaDto();
            this.ofertaActual.setFechaInicio(new Date());
            cargarOfertasPublicadas();

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }
    
    // Método para cargar datos en el formulario al dar click en "Editar"
    public void cargarParaEditar(OfertaLaboral oferta) {
        this.ofertaActual = new OfertaDto();
        this.ofertaActual.setIdOferta(oferta.getIdOferta());
        this.ofertaActual.setTitulo(oferta.getTitulo());
        this.ofertaActual.setDescripcion(oferta.getDescripcion());
        this.ofertaActual.setSalarioPromedio(oferta.getSalarioPromedio());
        
        // LocalDate -> Date
        if(oferta.getFechaInicio() != null)
            this.ofertaActual.setFechaInicio(Date.from(oferta.getFechaInicio().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if(oferta.getFechaCierre() != null)
            this.ofertaActual.setFechaCierre(Date.from(oferta.getFechaCierre().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            
        // IDs
        if(oferta.getCategoria() != null) this.ofertaActual.setIdCategoria(oferta.getCategoria().getIdCategoria());
        if(oferta.getModalidad() != null) this.ofertaActual.setIdModalidad(oferta.getModalidad().getIdModalidad());
        if(oferta.getJornada() != null) this.ofertaActual.setIdJornada(oferta.getJornada().getIdJornada());
        if(oferta.getCiudad() != null) this.ofertaActual.setIdCiudad(oferta.getCiudad().getIdCiudad());
    }
    
    public void eliminarOferta(OfertaLaboral oferta) {
        ofertaRepository.delete(oferta);
        cargarOfertasPublicadas();
    }

    // --- GETTERS Y SETTERS ---
    // (Generar todos los Getters y Setters para las listas y el objeto ofertaActual)
    public OfertaDto getOfertaActual() { return ofertaActual; }
    public void setOfertaActual(OfertaDto ofertaActual) { this.ofertaActual = ofertaActual; }
    public List<OfertaLaboral> getListaOfertas() { return listaOfertas; }
    public List<CategoriaOferta> getListaCategorias() { return listaCategorias; }
    public List<ModalidadOferta> getListaModalidades() { return listaModalidades; }
    public List<JornadaOferta> getListaJornadas() { return listaJornadas; }
    public List<Ciudad> getListaCiudades() { return listaCiudades; }
}