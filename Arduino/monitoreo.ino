//Sensores corriente ACS712, sensibilidad: 5A-185mv/A, 20A-100mv/A, 30A-66mv/A


void monitoreo()  {
  
  muestreo();
  
  estadoAlambrado_vol = estadoAlambrado_vol * 12/2112;
  if (estadoAlambrado_vol < 8)  {
    estadoAlambrado = "false";
  }
  else  {
    estadoAlambrado = "true";
  }

  volBateria = volBateria * 12/2111;
  volPS = volPS *12/1445;

  
  corrBateria = corrBateria - 2890;
  if(abs(corrBateria)<= 8){
      corrBateria = 0;
    }
  corrBateria = corrBateria/261;

    corrPS = corrPS - 2924; 
  if(abs(corrPS)<= 10){
      corrPS = 0;
    }
  corrPS = corrPS/261;

  fechaHora();
  
}
