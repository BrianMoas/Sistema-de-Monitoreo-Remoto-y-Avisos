void envioDatos(){
    bool success = hayConexionConServidor();

    if(!success){
          agregarRegistro();
      }else{
        if(!hayRegistros()){
          String strRegistro  = generarStringRegistro();
          strRegistro.remove(0,1);
          Serial.print(strRegistro);
            requestRegistro(strRegistro);
          }else{
                agregarRegistro();
                enviarConjuntoDatos();
              }
      }
}

void enviarConjuntoDatos(){
  String registros = verRegistros();
  requestRegistros(registros);
  borrarRegistros();
  }
