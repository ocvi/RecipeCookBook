package com.example.kajza.kuharica.SplashScreen;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.kajza.kuharica.MainActivity;
import com.example.kajza.kuharica.R;
import com.example.kajza.kuharica.RegistrationModel.LoginActivity1;
import java.util.Random;



public class SplashScreen extends Activity {

    Thread splashThread;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_splash);
        img = (ImageView) findViewById(R.id.imgRandom);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        int[] slike = new int[]{R.drawable.app2,R.drawable.app3};
        Random random = new Random();
        int r = random.nextInt(slike.length);
        this.img.setImageDrawable(getResources().getDrawable(slike[r]));

        splashThread = new Thread(){

            @Override
            public void run() {

                try {
                    int waited = 0;
                    while (waited < 3500){
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this, LoginActivity1.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    SplashScreen.this.finish();
                }

            }


        };

        splashThread.start();


    }


}