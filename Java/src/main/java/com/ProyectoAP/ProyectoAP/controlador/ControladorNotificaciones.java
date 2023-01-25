package com.ProyectoAP.ProyectoAP.controlador;

import com.ProyectoAP.ProyectoAP.modelo.Registro;
import com.ProyectoAP.ProyectoAP.servicio.ServicioNotificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControladorNotificaciones {

    @Autowired
    ServicioNotificacion servicioNotificacion;

    @RequestMapping(value = "/notificacion", method = RequestMethod.GET)
    String getNotificaciones() {
        try {
            return servicioNotificacion.getNotificaciones();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }

    @RequestMapping(value = "/notificacion", method = RequestMethod.DELETE)
    boolean eliminarNotificacion() {
        try {
            return servicioNotificacion.eliminarNotificacion();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
