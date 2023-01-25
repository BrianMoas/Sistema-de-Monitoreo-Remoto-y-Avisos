package com.ProyectoAP.ProyectoAP.modelo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bateria")
public class Bateria {
    @Id
    private ObjectId id;
    private double volMax;
    private double volMin;

    public Bateria() {
    }

    public double getVolMax() {
        return volMax;
    }

    public void setVolMax(double volMax) {
        this.volMax = volMax;
    }

    public double getVolMin() {
        return volMin;
    }

    public void setVolMin(double volMin) {
        this.volMin = volMin;
    }
}
