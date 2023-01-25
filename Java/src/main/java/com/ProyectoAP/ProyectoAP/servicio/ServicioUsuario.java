package com.ProyectoAP.ProyectoAP.servicio;

import com.ProyectoAP.ProyectoAP.modelo.Usuario;
import com.ProyectoAP.ProyectoAP.repositorio.RepositorioUsuario;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ServicioUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;


    public String validarUsuarioContrasena(String nombre, String contrasena) {
        nombre = nombre.toLowerCase();
        contrasena = contrasena.toLowerCase();

        Usuario u = repositorioUsuario.validarUsuarioContrasena(nombre, contrasena);
        if (u == null) {
            return "";
        }else{
            logearUsuario(u);
            return u.getId().toString();
        }
    }

    private void logearUsuario(Usuario u) {
        u.setEstado(true);
        repositorioUsuario.save(u);
    }

    public boolean estaLogueado() {
        Usuario u = repositorioUsuario.findAll().get(0);
        if(u ==  null ||  !u.isEstado()){
            return false;
        }
        return true;
    }

    public void cerrarSesion() {

        Usuario u = repositorioUsuario.findAll().get(0);
        u.setEstado(false);
        repositorioUsuario.save(u);
    }
}
