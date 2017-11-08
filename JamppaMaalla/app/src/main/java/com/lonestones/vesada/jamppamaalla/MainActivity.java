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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;


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
        // get display object. Display = size and density of a logical display
        Display ruutu = getWindowManager().getDefaultDisplay();

        // get resolution
        Point ruutukoko = new Point();   // point = two integer coordinates
        ruutu.getSize(ruutukoko);      // gets display size = current _app window_ size


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

        // otetaan pikselin väri tausta-bitmapista ja "peittomaali":n arvoksi
        int pikseli = tausta.getPixel(20,20);
        peittomaali.setColor(pikseli);
        maali.setColor(Color.parseColor("#113366")); // luodaan toinenkin piirtoväri
        // R, G ja B -arvot saa erikseenkin tarvittaessa...
        // int redValue = Color.red(pikseli);
        // int blueValue = Color.blue(pikseli);
        // int greenValue = Color.green(pikseli);

        //  J ympyröillä ja neliöillä
        canvas.drawRect(230,50,330,290, maali); // rect
        canvas.drawCircle(200,300,130,maali);   // circle
        canvas.drawRect(50,100,250,300,peittomaali);  // J:n peitto-osa

        // aseta viivan paksuus
        maali.setStrokeWidth(30);

        piirraA(canvas, 350,430, maali);  // A
        piirraM(canvas, 630,430, maali);  // M
        piirraP(canvas, 920, 430, maali, peittomaali); // P
        piirraP(canvas, 1190, 430, maali, peittomaali);// P
        piirraA(canvas, 1390,430, maali); // A

        piirraM(canvas, 100,850, maali);  // M
        piirraA(canvas, 350,850, maali);  // A
        piirraA(canvas, 630,850, maali);  // A
        piirraL(canvas, 920, 850, maali); // L
        piirraL(canvas, 1190, 850, maali);// L
        piirraA(canvas, 1390,850, maali); // A


        // fontin koon määrittely. Lasketaan ensin suhdeluku ruudun koon perusteella
        double kuvasuhde = (Math.sqrt(canvas.getWidth() * canvas.getHeight()))/1200;
        // tekstin koko
        maali.setTextSize((float) (getResources().getDimensionPixelSize(R.dimen.fonttikoko) * kuvasuhde));
        // text
        canvas.drawText("screen size: "+ruutukoko.x+" x " + ruutukoko.y,20,ruutukoko.y-50,maali);


        // smooths out the edges of what is being drawn
        maali.setColor(Color.GREEN);
        maali.setAntiAlias(true);
        //  transfer mode defines how source pixels are composited with the destination pixels(target content)
        maali.setXfermode(new PorterDuffXfermode(Mode.ADD));

        canvas.drawCircle(1200,700,300,maali);

        // väripallot
        int alpha = 255;
        int r = Color.argb(alpha, 255, 0, 0);
        int g = Color.argb(alpha, 0, 255, 0);
        int b = Color.argb(alpha, 0, 0, 255);
        maali.setColor(r);
        canvas.drawCircle(300,430,50,maali);
        maali.setColor(g);
        canvas.drawCircle(330,470,50,maali);
        maali.setColor(b);
        canvas.drawCircle(270,470,50,maali);


        // liitetään bitmappi view:hun
        alkukuvaView.setImageBitmap(bitmappi);
    }

    // muuta kirjaimet ruudun mukaan venyviksi, esim 1/4-osa ruutua korkeus ???

    public void piirraA(Canvas canvas, float xsij, float ysij, Paint maali){
        // piirrä viivoilla A
        canvas.drawLine(xsij,ysij,xsij+100,ysij-380, maali);
        canvas.drawLine(xsij+100,ysij-380,xsij+200,ysij, maali);
        canvas.drawLine(xsij+50,ysij-130,xsij+150,ysij-130, maali);
    }

    public void piirraM(Canvas canvas, float xsij, float ysij, Paint maali){
        // piirrä viivoilla M
        canvas.drawLine(xsij,ysij,xsij,ysij-380, maali);
        canvas.drawLine(xsij,ysij-380,xsij+100,ysij-20, maali);
        canvas.drawLine(xsij+100,ysij-20,xsij+200,ysij-380, maali);
        canvas.drawLine(xsij+200,ysij-380,xsij+200,ysij, maali);
    }

    public void piirraP(Canvas canvas, float xsij, float ysij, Paint maali, Paint peittomaali){
        // piirrä viivoilla P
        canvas.drawLine(xsij,ysij,xsij,ysij-300, maali);
        canvas.drawCircle(xsij+93,ysij-285,110, maali);
        canvas.drawCircle(xsij+93,ysij-285,70, peittomaali);

        // drawArc & drawOval  require API 21
    }

    public void piirraL(Canvas canvas, float xsij, float ysij, Paint maali){
        // piirrä viivoilla P
        canvas.drawLine(xsij,ysij,xsij,ysij-380, maali);
        canvas.drawLine(xsij-10,ysij-10,xsij+150,ysij-10, maali);
    }


    @Override
    public void onClick(View view){
        // pelin aloitus napista -> uusi intent GameActivity-luokasta
        //   startActivity (new Intent(this, GameAction.class));
    }
}
