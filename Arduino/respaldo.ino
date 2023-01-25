void agregarRegistro(){

      const char* registroConvertido = generarStringRegistro().c_str();
      if(!hayRegistros()){
            writeFile(SD, "/registros.txt", " ");
            appendFile(SD, "/registros.txt", registroConvertido);
      }else{
        appendFile(SD, "/registros.txt", registroConvertido);    
      } 
  }

String verRegistros(){
          return readFile(SD, "/registros.txt"); 
  }

void borrarRegistros(){
      deleteFile(SD, "/registros.txt");
    }

boolean hayRegistros(){
    return hayDatos(SD, "/registros.txt");
  }
