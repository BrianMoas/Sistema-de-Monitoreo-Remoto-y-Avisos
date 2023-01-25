void requestRegistro(String mensaje){
      WiFiClientSecure *client = new WiFiClientSecure;
  if(client) {
    client -> setCACert(rootCACertificate);

    {
      // Add a scoping block for HTTPClient https to make sure it is destroyed before WiFiClientSecure *client is 
      HTTPClient https;
  
      Serial.print("[HTTPS] begin...\n");
      if (https.begin(*client, "https://proyectoaport.hopto.org:8443/registro")) {  // HTTPS
        Serial.print("[HTTPS] POST...\n");
        // start connection and send HTTP header
        https.addHeader("Content-Type", "application/json");
        int httpCode = https.POST(mensaje);
  
        // httpCode will be negative on error
        if (httpCode > 0) {
          // HTTP header has been send and Server response header has been handled
          Serial.printf("[HTTPS] POST... code: %d\n", httpCode);
  
          // file found at server
          if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
            String payload = https.getString();
            Serial.println(payload);
          }
        } else {
          Serial.printf("[HTTPS] POST... failed, error: %s\n", https.errorToString(httpCode).c_str());
        }
  
        https.end();
      } else {
        Serial.printf("[HTTPS] Unable to connect\n");
      }

      // End extra scoping block
    }
  
    delete client;
  } else {
    Serial.println("Unable to create client");
  }
}

void requestRegistros(String mensaje){
      WiFiClientSecure *client = new WiFiClientSecure;
  if(client) {
    client -> setCACert(rootCACertificate);

    {
      // Add a scoping block for HTTPClient https to make sure it is destroyed before WiFiClientSecure *client is 
      HTTPClient https;
  
      Serial.print("[HTTPS] begin...\n");
      if (https.begin(*client, "https://proyectoaport.hopto.org:8443/registros")) {  // HTTPS
        Serial.print("[HTTPS] POST...\n");
        // start connection and send HTTP header
        https.addHeader("Content-Type", "application/json");
        int httpCode = https.POST(mensaje);
  
        // httpCode will be negative on error
        if (httpCode > 0) {
          // HTTP header has been send and Server response header has been handled
          Serial.printf("[HTTPS] GET... code: %d\n", httpCode);
  
          // file found at server
          if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
            String payload = https.getString();
            Serial.println(payload);
          }
        } else {
          Serial.printf("[HTTPS] POST... failed, error: %s\n", https.errorToString(httpCode).c_str());
        }
  
        https.end();
      } else {
        Serial.printf("[HTTPS] Unable to connect\n");
      }

      // End extra scoping block
    }
  
    delete client;
  } else {
    Serial.println("Unable to create client");
  }
}

boolean hayConexionConServidor(){
      boolean ret = false;
          WiFiClientSecure *client = new WiFiClientSecure;
    if(client) {
    client -> setCACert(rootCACertificate);

    {
      // Add a scoping block for HTTPClient https to make sure it is destroyed before WiFiClientSecure *client is 
      HTTPClient https;
  
      Serial.print("[HTTPS] begin...\n");
      if (https.begin(*client, "https://proyectoaport.hopto.org:8443/registros")) {  // HTTPS
        Serial.print("[HTTPS] GET...\n");
        // start connection and send HTTP header
        https.addHeader("Content-Type", "application/json");
        int httpCode = https.GET();
  
        // httpCode will be negative on error
        if (httpCode > 0) {
          // HTTP header has been send and Server response header has been handled
          Serial.printf("[HTTPS] GET... code: %d\n", httpCode);
  
          // file found at server
          if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
            String payload = https.getString();
            ret = true;
            Serial.println(payload);
          }
        } else {
          Serial.printf("[HTTPS] POST... failed, error: %s\n", https.errorToString(httpCode).c_str());
        }
  
        https.end();
      } else {
        Serial.printf("[HTTPS] Unable to connect\n");
      }

      // End extra scoping block
    }
  
    delete client;
  } else {
    Serial.println("Unable to create client");
  }
  return ret;
  }

String generarStringRegistro(){
      String str2a = ",{\"estadoAlambrado\":" + estadoAlambrado + ",";
      String str2b = "\"volBateria\":" + String(volBateria) + ",";
      String str2c = "\"corrBateria\":" + String(corrBateria) + ",";
      String str2d = "\"volPanelSolar\":" + String(volPS) + ",";
      String str2e = "\"corrPanelSolar\":" + String(corrPS) + ",";
      String str2f = "\"fecha\":" + String(fechaG) + ",";
      String str2g = "\"hora\":" + String(horaG) + "}";
      return str2a + str2b + str2c + str2d + str2e + str2f + str2g;
  }
