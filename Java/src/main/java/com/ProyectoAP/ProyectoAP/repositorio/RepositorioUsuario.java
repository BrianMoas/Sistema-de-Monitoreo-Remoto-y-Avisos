package com.ProyectoAP.ProyectoAP.repositorio;

import com.ProyectoAP.ProyectoAP.modelo.Usuario;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends MongoRepository<Usuario, Integer> {

    @Query("{ 'nombre' : ?0, 'contrasena' : ?1 }")
    Usuario validarUsuarioContrasena(String nombre, String contrasena);

    @Query("{ '_id' : ?0 }")
    Usuario findByObjectId(ObjectId id);
}
