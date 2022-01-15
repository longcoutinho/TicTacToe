package com.example.tictactoe;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;
    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder) {
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
    }
    @Override
    public void run(){
        long startTime = System.nanoTime();

        while(running) {
            Canvas canvas = null;
            try {
                // Get Canvas form Holder and lock it
                canvas = this.surfaceHolder.lockCanvas();

                // Synchronized
                synchronized (canvas) {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            }catch (Exception E) {
                //Log.v("long", E + "");
            }finally {
                if (canvas != null) {
                    //Unlock canvas
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        /*
        //long now = System.nanoTime();
        // time to redraw game
        //long waittime = (now - startTime) / 1000000;
        //if (waittime < 10) {
         //   waittime = 10;
        }
        System.out.println(" Wait time=" + waittime);
        try {
            //Sleep
            this.sleep(waittime);
        }catch(InterruptedException e) {
            //nothing
        }
        //startTime = System.nanoTime();
        //System.out.println(".");
         */
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
