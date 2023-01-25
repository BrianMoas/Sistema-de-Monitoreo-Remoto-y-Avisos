package com.ProyectoAP.ProyectoAP.modelo;

 public class Cronometro {

    private final long nanoSecondsPerMinute = 60000000000L;


    private long stopWatchStartTime = 0;
    private long stopWatchStopTime = 0;
    private boolean stopWatchRunning = false;


    public void stop() {
        this.stopWatchStopTime = System.nanoTime();
        this.stopWatchRunning = false;
    }

    public void restart(){
        this.stopWatchStartTime = 0;
        this.stopWatchStopTime = 0;
        this.stopWatchRunning = false;
        start();
    }

    private void start() {
        this.stopWatchStartTime = System.nanoTime();
        this.stopWatchRunning = true;
    }


    public long getElapsedMinutes() {
        long elapsedTime;
        if (stopWatchRunning)
            elapsedTime = (System.nanoTime() - stopWatchStartTime);
        else
            elapsedTime = (stopWatchStopTime - stopWatchStartTime);

        return elapsedTime / nanoSecondsPerMinute;
    }
}
