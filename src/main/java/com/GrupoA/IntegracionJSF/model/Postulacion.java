package com.GrupoA.IntegracionJSF.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "postulacion")
@Data
public class Postulacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulacion")
    private Integer idPostulacion;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    private OfertaLaboral oferta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_postulacion")
    private LocalDateTime fechaPostulacion;

    @Column(name = "estado_postulacion", length = 50)
    private String estadoPostulacion; // Aquí guardarás 'CONTRATADO' o 'RECHAZADO'

    @PrePersist
    public void prePersist() {
        if (this.fechaPostulacion == null) {
            this.fechaPostulacion = LocalDateTime.now();
        }
        if (this.estadoPostulacion == null) {
            this.estadoPostulacion = "POSTULADO";
        }
    }
}