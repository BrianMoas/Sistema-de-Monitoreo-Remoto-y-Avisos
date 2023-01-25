
async function getNotificaciones(){
        const request = await fetch('notificacion', {
        		method: 'GET',
        		headers: {
        			'Accept': 'application/json',
        			'Content-Type': 'application/json'
        		}
        	});
        	const res = await request.json();
        if(res.length === 0) return;

        var strBodyTabla = "";
    	var bodyTabla = document.getElementById("bodyTablaNotificaciones");

        document.getElementById("notificaciones").style.display = "flex";

    	//RELLENAR TABLA
    	for (var i = res.length - 1; i >= 0; i--) {
    		strBodyTabla += "<tr style='border-color: black';>" +
    			"<td style='text-align: center; vertical-align: middle;'>" + res[i].nombre + "</td>" +
    			"<td style='text-align: center; vertical-align: middle;'>" + res[i].descripcion + "</td>" +
    			"<td style='text-align: center; vertical-align: middle;'>" + res[i].fecha + "</td>" +
    			"<td style='text-align: center; vertical-align: middle;'>" + res[i].hora + "</td>" +
    			"</tr>";
    	};

    	bodyTabla.innerHTML = strBodyTabla;
};
async function eliminarNotificaciones(){
    const request = await fetch('notificacion', {
                                		method: 'DELETE',
                                		headers: {
                                			'Accept': 'application/json',
                                			'Content-Type': 'application/json'
                                		}
                                });
    document.getElementById("notificaciones").style.display = "none";
};

    //ACTUALIZACIONES
    async function actualizarP(){
        getNotificaciones();
    }

    setInterval('actualizarP()',60000);