//INICIAR SESION CON TECLA ENTER

var input = document.getElementById("contrasena");

    // EJECUTO FUNCION CUANDO PRESIONAN UNA TECLA
input.addEventListener("keypress", function(event) {
  if (event.key === "Enter") {
    //CANCELO LA ACCIÓN POR DEFAULT
    event.preventDefault();
    //DISPARO EL EVENTO CLICK DEL BOTÓN
    document.getElementById("btnIniciarSesion").click();
  }
});

// INICIAR SESION

async function IniciarSesion(){
    let datos = {};
    datos.nombre = document.getElementById('nombre').value;
    datos.contrasena = document.getElementById('contrasena').value;

    const request = await fetch('login', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nombre: datos.nombre,
            contrasena: datos.contrasena
        })
      });

    const response = await request.text();

    if(response != ""){
        location.href = "index.html";
    }else{
        Swal.fire({
                title : "ERROR",
                text : "Usuario y/o contraseña inválidos",
                icon : "error",
                timer : 2000,
                timerProgressBar : true,
                position : "center",
                allowEscapeKey : false,
                allowEnterKey : true,
                stopKeydownPropagation: false,
                confirmButtonText: 'OK',
                confirmButtonColor: '#0962E3',
                toast : true
                });
    }
};

