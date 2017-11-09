package com.lonestones.vesada.jamppamaalla;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

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


    // class constructor
    // Context is an Interface to global information about an application environment, abstract class
    //  ...for application-level operations such as launching activities, broadcasting and receiving intents, etc
    // super() calls the parent's class constructor (all the way back to Object) and it runs before the current class's constructor.
    public GameView(Context konteksti, int ruudunleveys, int ruudunkorkeus) {
        super(konteksti);

    }

    @Override
    public void run() {

    }

    public void pause() {

    }

    public void resume() {

    }
}
