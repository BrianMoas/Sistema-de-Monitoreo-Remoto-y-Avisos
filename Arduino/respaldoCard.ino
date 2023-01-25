String readFile(fs::FS &fs, const char * path){
    String res = "[";

    File file = fs.open(path);
    if(!file){
        Serial.println("Failed to open file for reading");
        return "";
    }
    
    while(file.available()){
        char dato = file.read();
        res = res + dato;
    }
    file.close();
    res.remove(1,2);
    return res + "]";
}

void writeFile(fs::FS &fs, const char * path, const char * message){
    Serial.printf("Writing file: %s\n", path);

    File file = fs.open(path, FILE_WRITE);
    if(!file){
        Serial.println("Failed to open file for writing");
        return;
    }
    if(file.print(message)){
        Serial.println("File written");
    } else {
        Serial.println("Write failed");
    }
    file.close();
}

void appendFile(fs::FS &fs, const char * path, const char * message){
    Serial.printf("Appending to file: %s\n", path);

    File file = fs.open(path, FILE_APPEND);
    if(!file){
        Serial.println("Failed to open file for appending");
        return;
    }
    if(file.print(message)){
        Serial.println("Message appended");
    } else {
        Serial.println("Append failed");
    }
    file.close();
}
 
  void deleteFile(fs::FS &fs, const char * path){
      Serial.printf("Deleting file: %s\n", path);
      if(fs.remove(path)){
          Serial.println("File deleted");
      } else {
          Serial.println("Delete failed");
      }
  }

boolean hayDatos(fs::FS &fs, const char * path){
    File file = fs.open(path);
    if(!file){
        return false;
    }else{
        return true;
      }
  }
