package com.ProyectoAP.ProyectoAP.controlador;

import com.ProyectoAP.ProyectoAP.modelo.Registro;
import com.ProyectoAP.ProyectoAP.servicio.ServicioRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ControladorRegistro {

    @Autowired
    ServicioRegistro servicioRegistro;

    @RequestMapping(value = "/registro", method = RequestMethod.POST)
    void nuevoRegistro(@RequestBody(required = false) Registro registro) {
        try {
            System.out.println(registro.toString());
            if(registro == null){
                servicioRegistro.NotificarOneSignal("¡ERROR: Datos recibidos, vacios!");
                servicioRegistro.CrearNotificacion("ERROR", "¡Datos recibidos, vacios!", LocalDate.now().toString(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                return;
            }

            servicioRegistro.nuevoRegistro(registro);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(value = "/registros", method = RequestMethod.POST)
    void nuevosRegistros(@RequestBody(required = false) List<Registro> registros) {

            if(registros == null){
                servicioRegistro.NotificarOneSignal("¡ERROR: Datos recibidos, vacios!");
                servicioRegistro.CrearNotificacion("ERROR", "¡Datos recibidos, vacios!", LocalDate.now().toString(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                return;
            }

            servicioRegistro.nuevosRegistros(registros);

    }

    @RequestMapping(value = "/registros", method = RequestMethod.GET)
    String todosLosRegistros() {
        try{
            return servicioRegistro.todosLosRegistros();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }

    @RequestMapping(value = "/registrosPorFecha", method = RequestMethod.GET)
    String registrosPorFecha(@RequestParam(required = true) String fecha) {
        try {
            return servicioRegistro.registrosPorFecha(fecha);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }

    @RequestMapping(value = "/registrosPorRango", method = RequestMethod.GET)
    String registrosPorRango(@RequestParam(required = true) String fechaI, @RequestParam(required = true) String fechaF) {
        try {
            return servicioRegistro.registrosPorRango(fechaI, fechaF);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "";
        }
    }

    @RequestMapping(value = "/registrosDePrueba", method = RequestMethod.POST)
    boolean registrosDePrueba() {
        try {
            return servicioRegistro.registrosDePrueba();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping() {
        return "Conexion estable";
    }
}
