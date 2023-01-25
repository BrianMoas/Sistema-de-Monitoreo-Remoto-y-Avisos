package com.ProyectoAP.ProyectoAP.controlador;
import com.ProyectoAP.ProyectoAP.modelo.Bateria;
import com.ProyectoAP.ProyectoAP.servicio.ServicioBateria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControladorBateria {
    @Autowired
    ServicioBateria servicioBateria;

    @RequestMapping(value = "/configuracionBateria", method = RequestMethod.PUT)
    boolean configuracionBateria(@RequestParam(required = false) double minimo, @RequestParam(required = false) double maximo) {
        try {
            if (minimo <= -1 || maximo <= -1) return false;
            return servicioBateria.configuracionBateria(minimo, maximo);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @RequestMapping(value = "/configuracionBateria", method = RequestMethod.GET)
    Bateria getConfiguracionBateria() {
        try {
            return servicioBateria.getConfiguracionBateria();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
