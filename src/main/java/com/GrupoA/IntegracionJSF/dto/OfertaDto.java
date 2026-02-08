package com.GrupoA.IntegracionJSF.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfertaDto {
    private Integer idOferta;
    
    private String titulo;
    private String descripcion;
    private BigDecimal salarioPromedio;
    
    private Date fechaInicio; 
    private Date fechaCierre;
    
    private Integer idModalidad;
    private Integer idCategoria;
    private Integer idJornada;
    private Integer idCiudad;
}
