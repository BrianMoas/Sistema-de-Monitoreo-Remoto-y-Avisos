package com.ProyectoAP.ProyectoAP.repositorio;

import com.ProyectoAP.ProyectoAP.modelo.Registro;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioRegistro extends MongoRepository<Registro, ObjectId> {

    @Query("{ 'fecha' : ?0 }")
    List<Registro> findByFecha(String fecha);

    @Query(value = "{ 'fecha' : {$gte : ?0, $lte: ?1 }}")
    List<Registro> findByRango(String fechaI, String fechaF);
}
