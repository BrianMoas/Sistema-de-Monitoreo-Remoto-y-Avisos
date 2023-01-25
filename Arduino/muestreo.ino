void muestreo(){
    double suma = 0;
    int pinAnalizar = 0;
  

  for(int i = 0; i<5; i++){
    switch(i){
        case 0: 
          pinAnalizar = estadoAlambrado_pin;
          break;
        case 1: 
          pinAnalizar = volBateria_pin;
          break;
        case 2: 
          pinAnalizar = corrBateria_pin;
          break;
        case 3: 
          pinAnalizar = volPS_pin;
          break;
        case 4: 
        pinAnalizar = corrPS_pin;
        break;
      }
      for (int j = 0; j <1000; j++){
          suma = suma + analogRead(pinAnalizar);
      }
      switch(i){
        case 0: 
          estadoAlambrado_vol = suma/1000;
          break;
        case 1: 
          volBateria = suma/1000;
          break;
        case 2: 
          corrBateria = suma/1000;
          break;
        case 3: 
          volPS = suma/1000;
          break;
        case 4: 
          corrPS = suma/1000;
          break;
      }
      suma = 0;
    }
  }
