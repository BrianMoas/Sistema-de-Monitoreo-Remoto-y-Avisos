package com.ProyectoAP.ProyectoAP.servicio;

import com.ProyectoAP.ProyectoAP.modelo.Notificacion;
import com.ProyectoAP.ProyectoAP.repositorio.RepositorioNotificacion;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioNotificacion {
    @Autowired
    RepositorioNotificacion repositorioNotificacion;

    public boolean crearNotificacion(Notificacion notificacion){
        if(notificacion == null) return false;
        repositorioNotificacion.insert(notificacion);
        return true;
    }

    public boolean eliminarNotificacion(){
        repositorioNotificacion.deleteAll();
        return true;
    }

    public String getNotificaciones(){
        List<Notificacion> lista = repositorioNotificacion.findAll();
        String json = new Gson().toJson(lista);
        return json;
    }

}
