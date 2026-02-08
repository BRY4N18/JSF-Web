package com.GrupoA.IntegracionJSF.controller;

import com.GrupoA.IntegracionJSF.model.Usuario;
import com.GrupoA.IntegracionJSF.repository.UsuarioRepository;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component("loginBean") 
@Scope("view")
@Data
public class LoginBean implements Serializable {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String correo;
    private String contrasena;

    public String login() {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(this.correo);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (passwordEncoder.matches(this.contrasena, usuario.getContrasena())) {
                
                FacesContext context = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context.getExternalContext().getSession(true);

                session.setAttribute("session_id_usuario", usuario.getIdUsuario());
                session.setAttribute("session_rol", usuario.getRol().getNombreRol());
                session.setAttribute("nombre_usuario", usuario.getNombre());
                
                return "/views/dashboard?faces-redirect=true"; 
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Credenciales incorrectas"));
        
        return null;
    }
}
