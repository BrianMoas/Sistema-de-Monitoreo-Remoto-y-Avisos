package com.ProyectoAP.ProyectoAP.servicio;

import com.ProyectoAP.ProyectoAP.modelo.Bateria;
import com.ProyectoAP.ProyectoAP.modelo.Cronometro;
import com.ProyectoAP.ProyectoAP.modelo.Notificacion;
import com.ProyectoAP.ProyectoAP.modelo.Registro;
import com.ProyectoAP.ProyectoAP.repositorio.RepositorioRegistro;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Service
public class ServicioRegistro {

    @Autowired
    RepositorioRegistro repositorioRegistro;

    @Autowired
    ServicioBateria servicioBateria;

    @Autowired
    ServicioNotificacion servicioNotificacion;

    Cronometro cronometro = new Cronometro();
    boolean segundos[] = {false, false, false};


    public void nuevoRegistro(Registro registro) {
        try {
            if (registro == null || registro.getFecha() == null || registro.getHora() == null || registro.getVolBateria() == 99
                    || registro.getCorrBateria() == 99 || registro.getVolPanelSolar() == 99 || registro.getCorrPanelSolar() == 99) {
                notificarOneSignal("¡ERROR: Datos recibidos, vacios!");
                crearNotificacion("ERROR", "¡Datos recibidos, vacios!", LocalDate.now().toString(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
                return;
            }
            registro = mejoraFechaYHora(registro);
            repositorioRegistro.insert(registro);
            cronometro.restart();
            examinarDatos(registro);

        } catch (Exception e) {
            notificarOneSignal(e.getMessage());
         }
    }

    public void nuevosRegistros(List<Registro> registros) {
        for(int i = 0; i<registros.size(); i++){
            nuevoRegistro(registros.get(i));
        }
    }


    private Registro mejoraFechaYHora(Registro registro) {
        String[] partesHora = registro.getHora().split(":");
        String[] partesFecha = registro.getFecha().split("-");

        registro.setHora(mejoraHora(partesHora));
        registro.setFecha(mejoraFecha(partesFecha));


        return registro;
    }

    private String mejoraFecha(String[] partesFecha) {
        String ano = partesFecha[0];
        String mes = partesFecha[1];
        String dia = partesFecha[2];

        if(mes.length() == 1){
            mes = "0"+mes;
        }
        if (dia.length() == 1){
            dia = "0"+dia;
        }

        return ano+"-"+mes+"-"+dia;
    }

    private String mejoraHora(String[] partesHora) {
        String horas = partesHora[0];
        String minutos = partesHora[1];

        if(horas.length() == 1){
            horas = "0"+horas;
        }
        if (minutos.length() == 1){
            minutos = "0"+minutos;
        }

        return horas+":"+minutos;
    }

    public String todosLosRegistros() throws InterruptedException {
        List<Registro> r = repositorioRegistro.findAll();
        String json = new Gson().toJson(r);
        controlarCronometro();
        return json;
    }
    private void controlarCronometro() throws InterruptedException {
        if(cronometro.getElapsedMinutes() > 0.8 && cronometro.getElapsedMinutes() < 3.2){
            if(cronometro.getElapsedMinutes() == 1 && !segundos[0]){
                notificarSinConexion();
                segundos[0] = true;
            }else if(cronometro.getElapsedMinutes() == 2 && !segundos[1]){
                notificarSinConexion();
                segundos[1] = true;
            }else if(cronometro.getElapsedMinutes() == 3 && !segundos[2]){
                notificarSinConexion();
                segundos[2] = true;
            }
        }
        if(cronometro.getElapsedMinutes() > 3.2) {
            cronometro.stop();
            segundos [0] = false;
            segundos [1] = false;
            segundos [2] = false;
        }
    }
    private void notificarSinConexion(){
        notificarOneSignal("ERROR: ¡Se esperan datos que no fueron recibidos!");
        crearNotificacion("ERROR", "¡Se esperan datos que no fueron recibidos!", LocalDate.now().toString(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }
    private void examinarDatos(Registro registro) throws InterruptedException {

        if( !registro.isEstadoAlambrado()) {
            notificarOneSignal("¡Ha sido desactivado el alambrado!");
            crearNotificacion("ALAMBRADO", "¡Se ha desactivado el alambrado!", LocalDate.now().toString(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

//            DELAY PARA QUE SE VEAN TODAS LAS NOTIFICACIONES (EN CASO DE QUE HAYAN MAS AL MISMO TIEMPO)
            TimeUnit.SECONDS.sleep(3);
        }
        if(estadoBateria(registro.getVolBateria())) {
            notificarOneSignal("¡Batería fuera de rango!");
            crearNotificacion("BATERÍA", "¡Batería fuera de rango!", LocalDate.now().toString(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
    }

    private boolean estadoBateria(double volBateria) {
        if( volBateria == 0) return true;

        Bateria bat = servicioBateria.getBateria();
//        MAXIMO POR DEFECTO ES 15, MINIMO POR DEFECTO ES 12
        return Math.abs(volBateria) > bat.getVolMax() || Math.abs(volBateria) < bat.getVolMin();
    }

//    NOTIFICAR ONE SIGNAL
    public void NotificarOneSignal(String mensaje){
        notificarOneSignal(mensaje);
    }
    private void notificarOneSignal(String mensaje) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic YWQ3NzM3ZDEtMDhhNy00Mzc0LThhMTgtOWNmNmJkMWY2MjJi");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    + "\"app_id\": \"aa42e551-37d7-4769-bad0-12997ea1f2df\","
                    + "\"included_segments\": [\"Subscribed Users\"],"
                    + "\"data\": {\"foo\": \"bar\"},"
                    + "\"contents\": {\"en\": \""+mensaje+"\"}"
//                    + "\"firefox_icon\" : \"img/loginI.ico\""
                    + "}";



            byte[] sendBytes = strJsonBody.getBytes(StandardCharsets.UTF_8);
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
//            System.out.println("httpResponse: " + httpResponse);

            if (httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), StandardCharsets.UTF_8);
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            } else {
                Scanner scanner = new Scanner(con.getErrorStream(), StandardCharsets.UTF_8);
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
//            System.out.println("jsonResponse:\n" + jsonResponse);
            return;
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

//    NOTIFICACION
    public void CrearNotificacion(String titulo, String descripcion, String fecha, String hora){
        crearNotificacion(titulo,descripcion,fecha,hora);
    }

    private void crearNotificacion(String titulo, String descripcion, String fecha, String hora) {
        Notificacion notificacion = new Notificacion();
        notificacion.setNombre(titulo);
        notificacion.setDescripcion(descripcion);
        notificacion.setFecha(fecha);
        notificacion.setHora(hora);
        notificacion.setEstado(false);
        servicioNotificacion.crearNotificacion(notificacion);
    }


//    GRAFICAS

    public String registrosPorFecha(String fecha) {
        List<Registro> r = repositorioRegistro.findByFecha(fecha);
        String json = new Gson().toJson(r);
        return json;
    }

    public String registrosPorRango(String fechaI, String fechaF) {
        List<Registro> r = repositorioRegistro.findByRango(fechaI, fechaF);
        String json = new Gson().toJson(r);
        return json;
    }

//    REGISTROS DE PRUEBA
    public boolean registrosDePrueba(){
        try {

            List<Registro> r = repositorioRegistro.findAll();
            if(!r.isEmpty()) return false;

            agregarRegistroPrueba(false, 13, 3.2, 5.4, -1, "2022-08-25", "13:18");
            agregarRegistroPrueba(false, 14, 5, 4, 0, "2022-07-26", "17:18");
            agregarRegistroPrueba(false, 14, 5, 4, 0, "2022-07-27", "10:18");
            agregarRegistroPrueba(false, 14, 5, 4, 0.3, "2022-07-28", "10:18");
            agregarRegistroPrueba(true, 12, 2, 5, 1, "2022-08-5", "10:18");
            agregarRegistroPrueba(true, 13, 1, 0.3, 1.2, "2022-08-5", "11:18");
            agregarRegistroPrueba(true, 15, 2, 3, 2, "2022-08-5", "12:18");
            agregarRegistroPrueba(false, 12, 3, 0, 2.5, "2022-08-08", "12:18");
            agregarRegistroPrueba(true, 15, -1, 0.3, 5, "2022-08-08", "12:28");
            agregarRegistroPrueba(true, 15, 0, 0.6, 2.4, "2022-08-08", "12:38");
            agregarRegistroPrueba(true, 11, 0.5, 2, 2, "2022-08-08", "12:48");
            agregarRegistroPrueba(true, 13.92, 1.53, 17.85, 3.11, "2022-08-09", "12:48");
            agregarRegistroPrueba(true, 13.01, 0.95, 8.5, 0.16, "2022-08-09", "12:58");
            agregarRegistroPrueba(false, 10.5, 0, 0.09, 0, "2022-08-12", "0:01");
            agregarRegistroPrueba(false, 10.48, 0, 0.06, 0, "2022-08-12", "1:07");
            agregarRegistroPrueba(false, 10.48, 0, 0.09, 0, "2022-08-12", "2:02");
            agregarRegistroPrueba(false, 10.45, 0, 0.1, 0, "2022-08-12", "3:08");
            agregarRegistroPrueba(false, 10.47, 0, 0.05, 0, "2022-08-12", "4:03");
            agregarRegistroPrueba(false, 10.47, 0, 0.06, 0, "2022-08-12", "5:09");
            agregarRegistroPrueba(false, 10.44, 0, 0.03, 0, "2022-08-12", "6:04");
            agregarRegistroPrueba(false, 10.46, 0, 0.59, 0, "2022-08-12", "7:10");
            agregarRegistroPrueba(false, 10.86, 0.06, 12.05, 0.05, "2022-08-12", "8:05");
            agregarRegistroPrueba(true, 14.91, 0.47, 20.55, 0.34, "2022-08-12", "9:01");
            agregarRegistroPrueba(true, 14.9, 0.36, 21.49, 0.24, "2022-08-12", "10:07");
            agregarRegistroPrueba(true, 14.18, 0.25, 21.65, 0.16, "2022-08-12", "11:02");
            agregarRegistroPrueba(true, 14.29, 0.17, 21.51, 0.11, "2022-08-12", "12:08");
            agregarRegistroPrueba(true, 14.92, 0.23, 21.65, 0.14, "2022-08-12", "13:03");
            agregarRegistroPrueba(true, 14.02, 0.31, 21.26, 0.2, "2022-08-12", "14:09");
            agregarRegistroPrueba(true, 13.78, 1.05, 20.57, 0.7, "2022-08-12", "15:07");
            agregarRegistroPrueba(true, 12.79, 0.02, 13.94, 0.01, "2022-08-12", "16:02");
            agregarRegistroPrueba(true, 12.78, 0.01, 13.94, 0, "2022-08-12", "17:08");
            agregarRegistroPrueba(true, 12.66, 0, 11, 0, "2022-08-12", "18:03");
            agregarRegistroPrueba(true, 12.59, 0, 0, 0, "2022-08-12", "19:09");
            agregarRegistroPrueba(true, 12.43, 0, 0, 0, "2022-08-12", "20:04");
            agregarRegistroPrueba(true, 12.37, 0, 0, 0, "2022-08-12", "21:10");
            agregarRegistroPrueba(true, 12.35, 0, 11, 0, "2022-08-12", "22:03");
            agregarRegistroPrueba(true, 12.33, 0, 0, 0, "2022-08-12", "23:09");
            agregarRegistroPrueba(true, 12, 2, 5, 1, "2022-08-15", "10:04");
            agregarRegistroPrueba(true, 10, 3, 2.5, 0.3, "2022-08-15", "11:10");
            agregarRegistroPrueba(false, 12, 0, 2.8, 0, LocalDate.now().toString(), "00:10");
            agregarRegistroPrueba(true, 13.8, 0.5, 0, 0.8, LocalDate.now().toString(), "01:10");
            agregarRegistroPrueba(false, 13, 0.8, 0, 1, LocalDate.now().toString(), "02:10");
            agregarRegistroPrueba(true, 12, 1, 0.9, 0, LocalDate.now().toString(), "03:10");


            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void agregarRegistroPrueba(boolean alambrado, double volBateria, double corrBateria, double volPS, double corrPS, String fecha, String hora) {
        Registro r = new Registro();
        r.setEstadoAlambrado(alambrado);
        r.setVolBateria(volBateria);
        r.setCorrBateria(corrBateria);
        r.setVolPanelSolar(volPS);
        r.setCorrPanelSolar(corrPS);
        r.setFecha(fecha);
        r.setHora(hora);
        repositorioRegistro.insert(r);
    }
}
