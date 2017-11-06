package com.lonestones.vesada.jamppamaalla;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

// implementoidaan View OnClickListenerillä -> onko käyttäjä "klikannut"
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aloitusNappi;
    ImageView alkukuvaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ruutu landscape-asentoon
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        piirraAlkuvalikko();
        }

        
    public void piirraAlkuvalikko(){
        // "aloitusnappi" näytölle
        aloitusNappi = (Button) findViewById(R.id.starttinappi);
        // ja nappiin clickListener
        aloitusNappi.setOnClickListener(this);


        // selosta tästä eteenpäinkin , ja mitkä importit tarvitaan?
        alkukuvaView = (ImageView) this.findViewById(R.id.AlkukuvaView);

        Paint maali = new Paint();
        maali.setColor(Color.BLUE);

        // Each pixel is stored on 2 bytes and only the RGB channels are encoded: red is stored with 5 bits of precision (32 possible values), green is stored with 6 bits of precision (64 possible values) and blue is stored with 5 bits of precision.
        Bitmap bitmappi = Bitmap.createBitmap(300,600, Bitmap.Config.RGB_565);

        // Create a new canvas to draw on, and link it to the bitmap that we created above.
        Canvas canvas = new Canvas(bitmappi);
        // Fill the entire canvas with a red color.
        canvas.drawColor(Color.RED);
        // Draw a rectangle inside our image
        canvas.drawRect(25, 50, 75, 150, maali);

        alkukuvaView.setImageBitmap(bitmappi);
    }


    @Override
    public void onClick(View view){
        // pelin aloitus napista -> uusi intent GameActivity-luokasta
     //   startActivity (new Intent(this, GameAction.class));
    }
}
