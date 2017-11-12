package com.lonestones.vesada.jamppamaalla;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.os.Handler;

/**
 * Created by Vesada on 9.11.2017.
 */

public class Jamppa {

    // jamppa-bitmap  Bitmap[] frames = new Bitmap[10] //10 frames
    private Bitmap jamppaBitmap;
    private Bitmap jamppaBitmap2;
    // x & y
    private int x;
    private int y;
    private int xMax;
    private int yMax;
    private int xMin;
    private int yMin;
    private boolean meneeYlos;
    private int nopeus;

    private boolean jamppaFrame;
    private Handler handler;
    private Runnable runnable;

    public Jamppa(Context konteksti, int ruudunleveys, int ruudunkorkeus) {
        x = 50;
        nopeus = 7;

        // get bitmaps from resources
        jamppaBitmap = BitmapFactory.decodeResource(konteksti.getResources(), R.drawable.jamppa);
        jamppaBitmap2 = BitmapFactory.decodeResource(konteksti.getResources(), R.drawable.jamppa2);

        // max y for jamppa = screen height - jamppa height
        yMax = ruudunkorkeus - jamppaBitmap.getHeight();
        // y at bottom
        y = yMax;
        yMin = 70; // tämä säädettävä oikein myöhemmin

        // moves
        meneeYlos = true;

        // Jamppa framechange handler
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                // change frames
                if (jamppaFrame){
                    jamppaFrame = false;
                } else {
                    jamppaFrame = true;
                }
                handler.postDelayed(this, 300); // 300ms delay
            }
        };
        runnable.run();

        // vaihtoehtoisia lataustapoja:
        // base  = getDocumentBase();
        // hahmo = getImage(base, "data/kuva.png");

        // anim = new Animaatio();
        // anim.addFrame(kuva, 50); // = frame + duration

        // SDcardilta lataaminen olisi näin:
        //        ImageView image = (ImageView) findViewById(R.id.test_image);
        //        Bitmap bMap = BitmapFactory.decodeFile("/sdcard/test2.png");
        //        image.setImageBitmap(bMap);
    }


    // animate jamppa-frames
    public Bitmap getJamppaBitmap() {
        if (jamppaFrame) {
            return jamppaBitmap;
        } else {
            return jamppaBitmap2;
        }
    }

    // jampan liike tässä vaiheessa vain TOUCH = alas, NO TOUCH = ylös
    public void update() {
        if (meneeYlos) {
            if (y > yMin) {
                y -= 5;
            }
        } else {
            if (y < yMax){
                y +=5;
            }
        }

    }

    public int getX() { return x;    }

    public int getY() { return y;    }

    public int getNopeus() { return nopeus;    }

    public void meneYlos() { meneeYlos = true; }

    public void meneAlas() { meneeYlos = false;}
}


