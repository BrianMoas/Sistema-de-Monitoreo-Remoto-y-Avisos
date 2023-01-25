/*  string1:  "http://55.55.55.55:8080/registro/25-07-2022/10:55:28"  */

void fechaHora() {

  //Consulto hora en ntp server
  const char* ntpServer = "pool.ntp.org";
  const long  gmtOffset_sec = 3600*(-3);
  const int   daylightOffset_sec = 0;

  configTime(gmtOffset_sec, daylightOffset_sec, ntpServer);  //init and get the time
  struct tm timeinfo;
  if(!getLocalTime(&timeinfo)){
    Serial.println("Failed to obtain time");
    return;
  }
  getLocalTime(&timeinfo);

  int segundo = timeinfo.tm_sec;
  int minuto = timeinfo.tm_min;
  int hora = timeinfo.tm_hour;
  int dia = timeinfo.tm_mday;
  int mes = timeinfo.tm_mon +1;
  int anio = timeinfo.tm_year +1900;

  fechaG = "\"" + String(anio) + "-" + String(mes) + "-" + String (dia) + "\"";
  horaG = "\"" + String(hora) + ":" + String(minuto) + "\"";
}
