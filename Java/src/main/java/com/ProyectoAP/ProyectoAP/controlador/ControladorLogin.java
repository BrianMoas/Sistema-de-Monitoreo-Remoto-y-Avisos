package com.ProyectoAP.ProyectoAP.controlador;

import com.ProyectoAP.ProyectoAP.modelo.Usuario;
import com.ProyectoAP.ProyectoAP.servicio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControladorLogin {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String validarUsuarioContrasena(@RequestBody Usuario usuario) {
        try {
            return servicioUsuario.validarUsuarioContrasena(usuario.getNombre(), usuario.getContrasena());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    boolean estaLogueado() {
        try {
            return servicioUsuario.estaLogueado();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.PUT)
    void cerrarSesion() {
        try{
            servicioUsuario.cerrarSesion();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
