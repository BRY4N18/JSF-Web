package com.GrupoA.IntegracionJSF.service;

import com.GrupoA.IntegracionJSF.model.Usuario;

public interface IUsuarioService {
    // Definimos qué puede hacer nuestro servicio
    void registrarUsuarioNormal(Usuario usuario);
    void registrarEmpresaCompleta(Usuario usuario, String nombreEmp, String desc, String web, String ruc);
    void registrarUsuarioConAccesoBD(Usuario usuario);
}