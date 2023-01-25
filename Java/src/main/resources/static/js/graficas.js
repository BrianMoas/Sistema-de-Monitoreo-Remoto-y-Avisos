    // FUNCIONES PARA CREACION DE LAS GRAFICAS------------------------------------------------------

    let myChart;
    let graficaCreada = false;

    async function getGraphPorFecha(fecha) {

    	const request = await fetch('registrosPorFecha?fecha=' + fecha, {
    		method: 'GET',
    		headers: {
    			'Accept': 'application/json',
    			'Content-Type': 'application/json'
    		}
    	});


    	const datosRecibidos = await request.json();

    	const graphDatos = datosRecibidos.map((dato, index) => {
    		let horaObject = {};
    		horaObject.hora = dato.fecha + " " + dato.hora;
    		horaObject.datos = {};
    		horaObject.datos.voltajeBateria = dato.volBateria;
    		horaObject.datos.corrienteBateria = dato.corrBateria * 3;
    		horaObject.datos.voltajePanelSolar = dato.volPanelSolar;
    		horaObject.datos.corrientePanelSolar = dato.corrPanelSolar * 3;
    		if (dato.estadoAlambrado) {
    			dato.estadoAlambrado = 10;
    		} else {
    			dato.estadoAlambrado = 0;
    		}
    		horaObject.datos.estadoAlambrado = dato.estadoAlambrado;
    		return horaObject;
    	})

    	const data = {
    		datasets: [{
    				label: 'Voltaje Batería (V)',
    				backgroundColor: 'rgb(0, 0, 0)',
    				borderColor: 'rgb(0, 0, 0)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.voltajeBateria'
    				}
    			},
    			{
    				label: 'Corriente Batería (AmpX3)',
    				backgroundColor: 'rgb(128, 128, 128)',
    				borderColor: 'rgb(128, 128, 128)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.corrienteBateria'
    				}
    			},
    			{
    				label: 'Voltaje Panel Solar (V)',
    				backgroundColor: 'rgb(255, 128, 0)',
    				borderColor: 'rgb(255, 128, 0)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.voltajePanelSolar'
    				}
    			},
    			{
    				label: 'Corriente Panel Solar (AmpX3)',
    				backgroundColor: 'rgb(255, 225, 0)',
    				borderColor: 'rgb(255, 225, 0)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.corrientePanelSolar'
    				}
    			},
    			{
    				label: 'Estado Alambrado (On/Off)',
    				backgroundColor: 'rgb(0, 0, 255)',
    				borderColor: 'rgb(0, 0, 255)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.estadoAlambrado'
    				}
    			}
    		]
    	};

    	const config = {
        	type: 'line',
        	data: data,
        	options: {
        		plugins: {
        			legend: {
        				position: 'left'
        			}
        		},
        		responsive: true
        	}
        };

    	myChart = new Chart(
    		document.getElementById('myChart'),
    		config);
    };


    //SEPARADOR CODIGO TIPD DE GRAFICAS ---------------------------------------------------------

    async function getGraphPorRango(fechaI, fechaF) {

    	const request = await fetch('registrosPorRango?fechaI=' + fechaI + '&fechaF=' + fechaF, {
    		method: 'GET',
    		headers: {
    			'Accept': 'application/json',
    			'Content-Type': 'application/json'
    		}
    	});



    	const datosRecibidos = await request.json();

    	const graphDatos = datosRecibidos.map((dato, index) => {
    		let horaObject = {};
    		horaObject.hora = dato.fecha + " " + dato.hora;
    		horaObject.datos = {};
    		horaObject.datos.voltajeBateria = dato.volBateria;
    		horaObject.datos.corrienteBateria = dato.corrBateria * 3;
    		horaObject.datos.voltajePanelSolar = dato.volPanelSolar;
    		horaObject.datos.corrientePanelSolar = dato.corrPanelSolar * 3;
    		if (dato.estadoAlambrado) {
    			dato.estadoAlambrado = 10;
    		} else {
    			dato.estadoAlambrado = 0;
    		}
    		horaObject.datos.estadoAlambrado = dato.estadoAlambrado;
    		return horaObject;
    	})


    	const data = {
    		datasets: [{
    				label: 'Voltaje Batería (V)',
    				backgroundColor: 'rgb(0, 0, 0)',
    				borderColor: 'rgb(0, 0, 0)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.voltajeBateria'
    				}
    			},
    			{
    				label: 'Corriente Batería (AmpX3)',
    				backgroundColor: 'rgb(128, 128, 128)',
    				borderColor: 'rgb(128, 128, 128)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.corrienteBateria'
    				}
    			},
    			{
    				label: 'Voltaje Panel Solar (V)',
    				backgroundColor: 'rgb(255, 128, 0)',
    				borderColor: 'rgb(255, 128, 0)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.voltajePanelSolar'
    				}
    			},
    			{
    				label: 'Corriente Panel Solar (AmpX3)',
    				backgroundColor: 'rgb(255, 225, 0)',
    				borderColor: 'rgb(255, 225, 0)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.corrientePanelSolar'
    				}
    			},
    			{
    				label: 'Estado Alambrado (On/Off)',
    				backgroundColor: 'rgb(0, 0, 255)',
    				borderColor: 'rgb(0, 0, 255)',
    				borderWidth: 1.5,
    				data: graphDatos,
    				parsing: {
    					xAxisKey: 'hora',
    					yAxisKey: 'datos.estadoAlambrado'
    				}
    			}
    		]
    	};

    	const config = {
    		type: 'line',
    		data: data,
    		options: {
    			plugins: {
    				legend: {
    					position: 'left'
    				}
    			},
    			responsive: true
    		}
    	};

    	myChart = new Chart(
    		document.getElementById('myChart'),
    		config);
    }



    //FIN FUNCIONES PARA CREACION DE LAS GRAFICAS-----------------------------------------------------


    function getFiltro(filtro) {

    	validarNoRepeticionGraph();

    	var rango = document.getElementById('rangoDeFechas');
    	var dia = document.getElementById('horasFecha');

    	if (filtro.value === "rangoFechas") {
    		rango.style.display = 'block';
    		dia.style.display = 'none';
    	} else if (filtro.value === "dia") {
    		rango.style.display = 'none';
    		dia.style.display = 'block';
    	} else {
    		rango.style.display = 'none';
    		dia.style.display = 'none';
    		graficarHoy();
    	}

    }

    function graficarHoy() {
    	validarNoRepeticionGraph();

    	var fecha = new Date().toISOString().slice(0, 10);
    	getGraphPorFecha(fecha);
    	graficaCreada = true;
    }

    function graficarDia() {
    	validarNoRepeticionGraph();

    	var fecha = document.getElementById('fecha').value;

    	getGraphPorFecha(fecha);

    	graficaCreada = true;
    }

    function graficarRango() {

    	validarNoRepeticionGraph();


    	var fechaI = document.getElementById('fechaI').value;
    	var fechaF = document.getElementById('fechaF').value;


    	getGraphPorRango(fechaI, fechaF);

    	graficaCreada = true;
    }

    function validarNoRepeticionGraph() {
    	if (!graficaCreada) {
    		return;
    	}
    	if (myChart) {
    		myChart.destroy();
    	}
    }

    //ACTUALIZACIONES
        async function actualizarGraph(){
            graficarHoy();
        }

        setInterval('actualizarGraph()',60000);
