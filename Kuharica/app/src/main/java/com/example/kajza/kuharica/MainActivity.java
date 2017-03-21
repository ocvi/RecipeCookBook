package com.example.kajza.kuharica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.kajza.kuharica.BaseActivity.BaseActivity;
import com.example.kajza.kuharica.RecipeModel.Deserti;
import com.example.kajza.kuharica.RecipeModel.GlavnaJela;
import com.example.kajza.kuharica.RecipeModel.Predjela;
import com.example.kajza.kuharica.RecipeModel.Salate;


public class MainActivity extends BaseActivity {

    ImageView deserti;
    ImageView glavna;
    ImageView predjela;
    ImageView salate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        deserti = (ImageView) findViewById(R.id.category1);
        deserti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent deserti = new Intent(MainActivity.this, Deserti.class);
                startActivity(deserti);
            }
        });

        glavna = (ImageView) findViewById(R.id.category2);
        glavna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent glavna = new Intent(MainActivity.this, GlavnaJela.class);
                startActivity(glavna);
            }
        });
        predjela = (ImageView) findViewById(R.id.category3);
        predjela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent predjela = new Intent(MainActivity.this, Predjela.class);
                startActivity(predjela);
            }
        });
        salate = (ImageView) findViewById(R.id.category4);
        salate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salate = new Intent(MainActivity.this, Salate.class);
                startActivity(salate);

            }
        });


        new DohvatImenaRecepata().execute();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

}




