package com.ProyectoAP.ProyectoAP.modelo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "Registros")
public class Registro {

    @Id
    private ObjectId id;
    private boolean estadoAlambrado;
    private double volBateria;
    private double corrBateria;
    private double volPanelSolar;
    private double corrPanelSolar;
    private String fecha;
    private String hora;



    public Registro() {
    }

//GETTERS Y SETTERS
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public boolean isEstadoAlambrado() {
        return estadoAlambrado;
    }

    public void setEstadoAlambrado(boolean estadoAlambrado) {
        this.estadoAlambrado = estadoAlambrado;
    }

    public double getVolBateria() {
        return volBateria;
    }

    public void setVolBateria(double volBateria) {
        this.volBateria = volBateria;
    }

    public double getCorrBateria() {
        return corrBateria;
    }

    public void setCorrBateria(double corrBateria) {
        this.corrBateria = corrBateria;
    }

    public double getVolPanelSolar() {
        return volPanelSolar;
    }

    public void setVolPanelSolar(double volPanelSolar) {
        this.volPanelSolar = volPanelSolar;
    }

    public double getCorrPanelSolar() {
        return corrPanelSolar;
    }

    public void setCorrPanelSolar(double corrPanelSolar) {
        this.corrPanelSolar = corrPanelSolar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {

        this.hora = hora;
    }
/*
//    PRIVATE FUNCTIONS

    private LocalDate stringToLocalDate(String fecha) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fecha, formatterDate);

    }

    private LocalTime stringToLocalTime(String hora) {
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(hora, formatterTime);
    }
    */
    @Override
    public String toString() {
        return "Registro{" +
                "id=" + id +
                ", estadoAlambrado=" + estadoAlambrado +
                ", volBateria=" + volBateria +
                ", corrBateria=" + corrBateria +
                ", volPanelSolar=" + volPanelSolar +
                ", CorrPanelSolar=" + corrPanelSolar +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}
