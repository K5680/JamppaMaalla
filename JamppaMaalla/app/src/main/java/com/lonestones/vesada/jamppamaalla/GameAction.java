package com.lonestones.vesada.jamppamaalla;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Vesada on 8.11.2017.
 */
// manifestiin lisätty: <activity android:name=".GameAction"></activity>

public class GameAction extends AppCompatActivity{
    // declare gameview
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // get nappaa ruudunkoko-extrat
        Bundle gameAction = getIntent().getExtras();
        int ruudunleveys = gameAction.getInt("ruudunleveys");
        int ruudunkorkeus = gameAction.getInt("ruudunkorkeus");

        // pass screen size to GameView constructor
        gameView = new GameView(this, ruudunleveys, ruudunkorkeus);
        setContentView(gameView); // sets the content of the view (=gameView) to screen
    }

    // pause game
    @Override // method overrides superclass's method, invoke the overridden method with "super"
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    // resume game
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
