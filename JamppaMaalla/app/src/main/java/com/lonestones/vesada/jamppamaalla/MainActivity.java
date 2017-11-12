package com.lonestones.vesada.jamppamaalla;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;


/**
 * Created by Vesada on 6.11.2017.
 * student.jamk.fi/~K5680
 */


// implementoidaan View OnClickListenerillä -> onko käyttäjä "klikannut"
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aloitusNappi;
    ImageView alkukuvaView;
    private Display ruutu;
    private Point ruutukoko;

    private int rk; // "ruutukerroin", jolla säädetään piirretyt kirjaimet ruutukoolle sopiviksi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ruutu landscape-asentoon
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // get display object. Display = size and density of a logical display
        ruutu = getWindowManager().getDefaultDisplay();

        // get resolution
        ruutukoko = new Point();   // point = two integer coordinates
        ruutu.getSize(ruutukoko);      // gets display size = current _app window_ size

        piirraAlkuvalikko();
    }


    public void piirraAlkuvalikko(){
        // "aloitusnappi" näytölle
        aloitusNappi = (Button) findViewById(R.id.starttinappi);
        // ja nappiin clickListener
        aloitusNappi.setOnClickListener(this);


        // selosta tästä eteenpäinkin , ja mitkä importit tarvitaan?
        alkukuvaView = (ImageView) this.findViewById(R.id.AlkukuvaView);

        Paint maali = new Paint();      // luodaan "maali"
        Paint peittomaali = new Paint();// luodaan "peittomaali"

        // Each pixel is stored on 2 bytes and only the RGB channels are encoded: red is stored with 5 bits of precision (32 possible values), green is stored with 6 bits of precision (64 possible values) and blue is stored with 5 bits of precision.
        Bitmap bitmappi = Bitmap.createBitmap(ruutukoko.x, ruutukoko.y, Bitmap.Config.RGB_565);
        // Create a new canvas to draw on, and link it to the bitmap that we created above.
        Canvas canvas = new Canvas(bitmappi);
        // Fill the entire canvas with a color.
        canvas.drawColor(Color.BLACK);

        // lataa tausta-bitmap
        Bitmap tausta = BitmapFactory.decodeResource(getResources(), R.drawable.jmm_logo);
        // aseta "tausta" canvasiin
        canvas.drawBitmap(tausta,0,0,null);

        // 1800 / 900 = 2. Rk = ruutukerroin. (Vaatinee jatkokehittelyä, että toimii eri ruutukoissa kunnolla?)
        rk = 1800/ruutukoko.x;

        // "nurmikko"
        maali.setColor(Color.parseColor("#008800"));
        canvas.drawRect(0,900/rk,ruutukoko.x,ruutukoko.y,maali);


        // otetaan pikselin väri tausta-bitmapista ja "peittomaali":n arvoksi
        int pikseli = tausta.getPixel(20,20);
        peittomaali.setColor(pikseli);
        maali.setColor(Color.parseColor("#113366")); // luodaan toinenkin piirtoväri

        // R, G ja B -arvot saa erikseenkin tarvittaessa...
        // int redValue = Color.red(pikseli);
        // int blueValue = Color.blue(pikseli);
        // int greenValue = Color.green(pikseli);

        //  J ympyröillä ja neliöillä
        canvas.drawRect(230/rk,50/rk,330/rk,290/rk, maali); // rect
        canvas.drawCircle(200/rk,300/rk,130/rk,maali);   // circle
        canvas.drawRect(50/rk,100/rk,250/rk,300/rk,peittomaali);  // J:n peitto-osa

        // aseta viivan paksuus
        maali.setStrokeWidth(30);

        piirraA(canvas, 350/rk,430/rk, maali);  // A
        piirraM(canvas, 630/rk,430/rk, maali);  // M
        piirraP(canvas, 920/rk, 430/rk, maali, peittomaali); // P
        piirraP(canvas, 1190/rk, 430/rk, maali, peittomaali);// P
        piirraA(canvas, 1390/rk,430/rk, maali); // A

        piirraM(canvas, 100/rk,850/rk, maali);  // M
        piirraA(canvas, 350/rk,850/rk, maali);  // A
        piirraA(canvas, 630/rk,850/rk, maali);  // A
        piirraL(canvas, 920/rk, 850/rk, maali); // L
        piirraL(canvas, 1190/rk, 850/rk, maali);// L
        piirraA(canvas, 1390/rk,850/rk, maali); // A


        // fontin koon määrittely. Lasketaan ensin suhdeluku ruudun koon perusteella
        double kuvasuhde = (Math.sqrt(canvas.getWidth() * canvas.getHeight()))/1500;
        // tekstin koko
        maali.setTextSize((float) (getResources().getDimensionPixelSize(R.dimen.fonttikoko) * kuvasuhde));
        // text
        maali.setColor(Color.WHITE);
        canvas.drawText("screen size: "+ruutukoko.x+" x " + ruutukoko.y,20,ruutukoko.y-30,maali);


        // smooths out the edges of what is being drawn
        maali.setAntiAlias(true);

        // vaihdetaan läpikuultava väri ympyrälle
        maali.setColor(Color.argb(50, 255, 0, 0));
        canvas.drawCircle(1300/rk,900/rk,500/rk,maali);

        //  transfer mode defines how source pixels are composited with the destination pixels(target content)
        maali.setXfermode(new PorterDuffXfermode(Mode.ADD));
        // vaihdetaan läpikuultava väri ympyröille
        maali.setColor(Color.argb(150, 255, 0, 0));
        canvas.drawCircle(1500/rk,150/rk,400/rk,maali);


        // väripallot
        int alpha = 200;
        int r = Color.argb(alpha, 255, 0, 0);
        int g = Color.argb(alpha, 0, 255, 0);
        int b = Color.argb(alpha, 0, 0, 255);
        maali.setColor(b);
        canvas.drawCircle(320/rk,320/rk,70/rk,maali);
        maali.setColor(g);
        canvas.drawCircle(340/rk,400/rk,70/rk,maali);
        maali.setColor(r);
        canvas.drawCircle(260/rk,380/rk,70/rk,maali);


        // liitetään bitmappi view:hun
        alkukuvaView.setImageBitmap(bitmappi);
    }




    public void piirraA(Canvas canvas, float xsij, float ysij, Paint maali){
        // piirrä viivoilla A
        canvas.drawLine(xsij,ysij,xsij+100/rk,ysij-380/rk, maali);
        canvas.drawLine(xsij+100/rk,ysij-380/rk,xsij+200/rk,ysij, maali);
        canvas.drawLine(xsij+50/rk,ysij-130/rk,xsij+150/rk,ysij-130/rk, maali);
    }

    public void piirraM(Canvas canvas, float xsij, float ysij, Paint maali){
        // piirrä viivoilla M
        canvas.drawLine(xsij,ysij,xsij,ysij-380/rk, maali);
        canvas.drawLine(xsij,ysij-380/rk,xsij+100/rk,ysij-20/rk, maali);
        canvas.drawLine(xsij+100/rk,ysij-20/rk,xsij+200/rk,ysij-380/rk, maali);
        canvas.drawLine(xsij+200/rk,ysij-380/rk,xsij+200/rk,ysij, maali);
    }

    public void piirraP(Canvas canvas, float xsij, float ysij, Paint maali, Paint peittomaali){
        // piirrä viivoilla P
        canvas.drawLine(xsij,ysij,xsij,ysij-300/rk, maali);
        canvas.drawCircle(xsij+93/rk,ysij-285/rk,110/rk, maali);
        canvas.drawCircle(xsij+93/rk,ysij-285/rk,70/rk, peittomaali);

        // drawArc & drawOval
        // & drawRoundRect        require API 21

    }

    public void piirraL(Canvas canvas, float xsij, float ysij, Paint maali){
        // piirrä viivoilla P
        canvas.drawLine(xsij,ysij,xsij,ysij-380/rk, maali);
        canvas.drawLine(xsij-10/rk,ysij-10/rk,xsij+150/rk,ysij-10/rk, maali);
    }


    @Override
    public void onClick(View view){
        // pelin aloitus napista -> uusi intent GameAction-luokasta
        // viedään ruudunkoko extrana

        Intent gameAction = new Intent(this, GameAction.class);
        gameAction.putExtra("ruudunleveys", ruutukoko.x);
        gameAction.putExtra("ruudunkorkeus", ruutukoko.y);
        startActivity(gameAction);
    }
}
