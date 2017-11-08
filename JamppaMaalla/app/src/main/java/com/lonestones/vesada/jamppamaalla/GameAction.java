package com.lonestones.vesada.jamppamaalla;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

/**
 * Created by Vesada on 8.11.2017.
 */

public class GameAction extends AppCompatActivity{
    // declare gameview
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle gameAction = getIntent().getExtras();
        int ruudunleveys = gameAction.getInt("ruudunleveys");
        int ruudunkorkeus = gameAction.getInt("ruudunkorkeus");
        // init game view object
        // pass screen size to GameView constructor = gameview size is screen size
        gameView = new GameView(this, ruudunleveys, ruudunkorkeus);
        setContentView(gameView);
    }
}
