
//-------------------------------------------------------------VALIDAR SI USUARIO ESTÁ O NO ESTÁ LOGUEADO
async function estaLogueado() {
	const request = await fetch('login/', {
		method: 'GET',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		}
	});
	const response = await request.json();
	if (response === false) {
		location.href = "login.html";
		return;
	}
};

//------------------------------------------------------------------------------------CERRAR SESION
async function cerrarSesion() {
	const request = await fetch('login/', {
		method: 'PUT'
	});
};

//---------------------------------------------------------------CUANDO CIERRE LA PESTAÑA O NAVEGADOR CIERRA SESION
window.addEventListener('beforeunload', function(e) {
	cerrarSesion();
});

//-----------------------------------------------------------------VER TODOS LOS REGISTROS EN TABLA
async function verRegistros() {
	const request = await fetch('registros', {
		method: 'GET',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		}
	});
	const res = await request.json();
	var strBodyTabla = "";
    var strBodyTabla1 = "";
	var bodyTabla = document.getElementById("bodyTabla");
	var bodyTabla1 = document.getElementById("bodyTabla1");


	//RELLENAR TABLAS
	for (var i = res.length - 1; i >= 0; i--) {
	    valorAlambrado = [];

	    if(res[i].estadoAlambrado === true){
        			    valorAlambrado[0] ="img/alambradoOn.png";
        			    valorAlambrado[1] = "Encendido";
        			}else{
        			    valorAlambrado[0] ="img/alambradoOff.png";
        			    valorAlambrado[1] = "Apagado";
        			}

        //TABLA VISIBLE
		strBodyTabla += "<tr>" +
			"<td><img width='24px' height='24px' src="+ valorAlambrado[0] +"></td>" +
			"<td>" + res[i].volBateria + "</td>" +
			"<td>" + res[i].corrBateria + "</td>" +
			"<td>" + res[i].volPanelSolar + "</td>" +
			"<td>" + res[i].corrPanelSolar + "</td>" +
			"<td>" + res[i].fecha + "</td>" +
			"<td>" + res[i].hora + "</td>" +
			"</tr>";

		//TABLA NO VISIBLE
		strBodyTabla1 += "<tr>" +
        			"<td>"+ valorAlambrado[1] +"</td>" +
        			"<td>" + res[i].volBateria + "</td>" +
        			"<td>" + res[i].corrBateria + "</td>" +
        			"<td>" + res[i].volPanelSolar + "</td>" +
        			"<td>" + res[i].corrPanelSolar + "</td>" +
        			"<td>" + res[i].fecha + "</td>" +
        			"<td>" + res[i].hora + "</td>" +
        			"</tr>";
	};

	bodyTabla.innerHTML = strBodyTabla;
	bodyTabla1.innerHTML = strBodyTabla1;

};

//------------------------------------------------------------------------------------CONFIGURACION BATERIA
async function configuracionBateria() {
    const request = await fetch('configuracionBateria', {
    		method: 'GET',
    		headers: {
    			'Accept': 'application/json',
    			'Content-Type': 'application/json'
    		}
    });
    const res = await request.json();

    const { value: formValues } = await Swal.fire({
      title: 'CONFIGURACIÓN VOLTAJE DE BATERÍA',
      html:
        '<div class="container"><div>'+
        '<label for="swal-input1">Máximo</label>'+
        '<input id="swal-input1" type="number" step=".01" value="'+ res.volMax +'" class="swal2-input"></div><div>' +
        '<label for="swal-input2">Mínimo</label>'+
        '<input id="swal-input2" type="number" step=".01" value="'+ res.volMin +'" class="swal2-input"></div></div>',
      focusConfirm: false,
      backdrop: true,
      background: '#F1F1F1',
      allowEscapeKey: false,
      allowEnterKey: false,
      confirmButtonColor: '#C5C3C3',
      confirmButtonText: 'ACEPTAR',
      confirmButtonColor: '#C5C3C3',
      preConfirm: () => {
        return [
          document.getElementById('swal-input1').value,
          document.getElementById('swal-input2').value
        ]
      }
    });

    if (formValues) {
        const request = await fetch('configuracionBateria?minimo='+formValues[1]+'&maximo='+formValues[0], {
                    		method: 'PUT',
                    		headers: {
                    			'Accept': 'application/json',
                    			'Content-Type': 'application/json'
                    		}
                    });
        const res = await request.text();
    };
};

//CARGAR DATOS DE PRUEBA
async function cargarDatosDePrueba(){
    const request = await fetch('registrosDePrueba', {
                        		method: 'POST',
                        		headers: {
                        			'Accept': 'application/json',
                        			'Content-Type': 'application/json'
                        		}
                        });
    const res = await request.text();

};

    //ACTUALIZACIONES
    async function actualizarUtil(){
        verRegistros();
//        listarNotificaciones();
    }

    setInterval('actualizarUtil()',60000);
