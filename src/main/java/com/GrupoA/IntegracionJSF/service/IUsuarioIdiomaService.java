package com.GrupoA.IntegracionJSF.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUsuarioIdiomaService {
    // Definimos el contrato: qué debe hacer el servicio sin decir cómo
    void registrarIdiomaConCertificado(
            Long idUsuario,
            Integer idIdioma,
            String nivel,
            MultipartFile archivo,
            String codigo
    );
}