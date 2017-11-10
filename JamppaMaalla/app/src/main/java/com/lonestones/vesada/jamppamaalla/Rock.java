package com.lonestones.vesada.jamppamaalla;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Random;

/**
 * Created by Vesada on 10.11.2017.
 */

public class Rock {

    // rock bitmap
    private Bitmap rockBitmap;
    private Bitmap scaledRockBitmap;
    private int ruudunlev;
    private int ruudunkork;
    private int x;
    private int y;
    private double kerroin;
    private int nopeus;
    private int kivenkoko;

    public Rock(Context konteksti, int ruudunleveys, int ruudunkorkeus) {
        ruudunlev = ruudunleveys;
        ruudunkork = ruudunkorkeus;
        // get bitmap from resources
        rockBitmap = BitmapFactory.decodeResource(konteksti.getResources(), R.drawable.kivi);

        // get image x-size
        kivenkoko = rockBitmap.getWidth();

        // randomize rock y & x & size
        Random noppa = new Random();
        y = (noppa.nextInt(ruudunkorkeus - 300))+200; // random number from zero to screen height
        x = noppa.nextInt(ruudunleveys);

        // scale the rocks with double random
        Random dblNoppa = new Random();
        double kerroin = (0.1 + (2 - 0.5) * dblNoppa.nextDouble())*kivenkoko;
        int koko = (int)kerroin;
        Log.d("kiven uusi koko ", "kerroin "+kerroin + " kivenkoko " + kivenkoko + " uusi koko "+koko);
        scaledRockBitmap = Bitmap.createScaledBitmap(rockBitmap,koko,koko,true);

    }

    public void update(int jampanNopeus) {
        x -= jampanNopeus;

        // rock off the screen -> back to right
        if (x < (0 - scaledRockBitmap.getWidth())) {
            x = ruudunlev;
            Random noppa = new Random();
            y = (noppa.nextInt(ruudunkork - 300))+200; // random number from zero to screen height

            // scale the rocks with double random
            Random dblNoppa = new Random();
            double kerroin = (0.1 + (2 - 0.5) * dblNoppa.nextDouble())*kivenkoko;
            int koko = (int)kerroin;

            scaledRockBitmap = Bitmap.createScaledBitmap(rockBitmap,koko,koko,true);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Bitmap getRockBitmap() { return scaledRockBitmap; }
}