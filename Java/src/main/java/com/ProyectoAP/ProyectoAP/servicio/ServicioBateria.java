package com.ProyectoAP.ProyectoAP.servicio;
import com.ProyectoAP.ProyectoAP.modelo.Bateria;
import com.ProyectoAP.ProyectoAP.repositorio.RepositorioBateria;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioBateria {
    @Autowired
    RepositorioBateria repositorioBateria;


    public boolean configuracionBateria(double minimo, double maximo) {
        List<Bateria> res = repositorioBateria.findAll();
        Bateria b = res.get(0);
        b.setVolMin(minimo);
        b.setVolMax(maximo);
        repositorioBateria.save(b);
        return true;
    }

    public Bateria getConfiguracionBateria() {
        List<Bateria> b = repositorioBateria.findAll();
        return b.get(0);
    }

    public Bateria getBateria(){
        ObjectId idEstadoBateria = new ObjectId("62ba073914e11864882b79f7");
//      ObjectId idEstadoBateria = new ObjectId("62b9d45d9b92456eb7c61032");

        return repositorioBateria.findById(idEstadoBateria).get();
    }

}
