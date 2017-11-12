package com.lonestones.vesada.jamppamaalla;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by Vesada on 9.11.2017.
 */

// SurfaceView = Provides a dedicated drawing surface embedded inside of a view hierarchy
// Runnable = instances are intended to be executed by a thread.
public class GameView extends SurfaceView implements Runnable{

    // game on / off
    volatile boolean pelimenossa;   // volatile = arvoa voidaan muuttaa eri säikeistä(thread)
    // game thread
    private Thread peliThread = null;
    // the player
    private Jamppa jamppa;
    // painting objects
    private Paint maali;
    private Canvas canvas;
    private int ruudunlev;
    private Paint maali2;
    // add rocks to list
    private ArrayList<Rock> rocks = new ArrayList<Rock>();



    // Allows you to control the surface size and format, edit the pixels in the surface, and monitor changes to the surface
    private SurfaceHolder surfaceHolder;

    // Context is an Interface to global information about an application environment, abstract class
    //  ...for application-level operations such as launching activities, broadcasting and receiving intents, etc
    // super() calls the parent's class constructor (all the way back to Object) and it runs before the current class's constructor.
    public GameView(Context konteksti, int ruudunleveys, int ruudunkorkeus) {
        super(konteksti);
        ruudunlev = ruudunleveys; // arvo talteen tausta-funktiota varten

        // init player, viedään myös ruudunkoko-tieto
        jamppa = new Jamppa(konteksti, ruudunleveys, ruudunkorkeus);

        // init surfaceView
        surfaceHolder = getHolder(); // access and control over underlying "surface"

        maali = new Paint();    // paints for drawing
        maali2 = new Paint();

        int rockCount = 5;
        for (int i = 0; i < rockCount; i++){
            Rock kivi = new Rock(konteksti, ruudunleveys, ruudunkorkeus);
            rocks.add(kivi);
        }

    }



    @Override
    public void run() {
        while (pelimenossa) {
            update();
            drawAll();
            control();
        }
    }

    // ruutuun piirtäminen
    public void drawAll() {
        // check surface validity
        if (surfaceHolder.getSurface().isValid()){

            //LOCK canvas
            canvas = surfaceHolder.lockCanvas();

            //draw background
            canvas.drawColor(Color.argb(255, 100, 170, 50));

            // sky
            maali2.setColor(Color.argb(255, 60, 190, 255));
            canvas.drawRect(0,0,ruudunlev,200,maali2);


            // draw rocks
            for (Rock kivi : rocks){
                canvas.drawBitmap(
                        kivi.getRockBitmap(),
                        kivi.getX(),
                        kivi.getY(),
                        maali);
            }

            // draw jamppa
            canvas.drawBitmap(
                    jamppa.getJamppaBitmap(),
                    jamppa.getX(),
                    jamppa.getY(),
                    maali);

            // UNLOCK canvas
            surfaceHolder.unlockCanvasAndPost(canvas);  // "flip screen?"
        }
    }


    public void update() {
        // update player position
        jamppa.update();

        // update rocks
        for (Rock kivi : rocks) {
            kivi.update(jamppa.getNopeus());
        }
    }

    public void control() {
        try {
            peliThread.sleep(17); // delay thread = framerate.
            // void sleep (long millis,int nanos)
            // millis 	long: the length of time to sleep in milliseconds
            // nanos 	int: 0-999999 additional nanoseconds to sleep
            } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        // pelimenossa false -> stop thread
        pelimenossa = false;
        try {
            peliThread.join();
        } catch (InterruptedException e){
                e.printStackTrace();
        }
    }

    public void resume() {
        // resume game
        pelimenossa = true;
        peliThread = new Thread(this);
        peliThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent liikeEventti){
        // get motion event, UP = touches screen, DOWN = no touch
        switch (liikeEventti.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                jamppa.meneYlos();

                break;
            case MotionEvent.ACTION_DOWN:
                jamppa.meneAlas();
                int r = jamppa.getY();
                Log.d("MyTagGoesHere", "jamppa alas"+r);
                break;
        }
        return true;
    }
}
