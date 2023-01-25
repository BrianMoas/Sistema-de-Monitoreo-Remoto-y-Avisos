package com.ProyectoAP.ProyectoAP.repositorio;

import com.ProyectoAP.ProyectoAP.modelo.Bateria;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioBateria extends MongoRepository<Bateria, ObjectId> {
}
