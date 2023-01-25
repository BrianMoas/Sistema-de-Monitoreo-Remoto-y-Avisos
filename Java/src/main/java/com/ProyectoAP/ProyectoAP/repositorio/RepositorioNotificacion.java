package com.ProyectoAP.ProyectoAP.repositorio;

import com.ProyectoAP.ProyectoAP.modelo.Notificacion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioNotificacion extends MongoRepository<Notificacion, ObjectId> {
}
