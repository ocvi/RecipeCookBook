package com.example.kajza.kuharica.RecipeModel;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kajza.kuharica.BaseActivity.BaseActivity;
import com.example.kajza.kuharica.MainActivity;
import com.example.kajza.kuharica.R;
import com.example.kajza.kuharica.api.InterfacePremaServisu;
import com.example.kajza.kuharica.api.ServiceGenerator;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlavnaJela extends BaseActivity {


    private static final String API_URL = "http://kajza.comxa.com/";
    private List<Recipe> recipe;
    private ListView list;
    @Override
    protected void onCreate (Bundle savedInstances) {

        super.onCreate(savedInstances);
        Fresco.initialize(this);
        setContentView(R.layout.activity_list);

        list = (ListView) findViewById(R.id.list);


        InterfacePremaServisu client = ServiceGenerator.createService(InterfacePremaServisu.class, API_URL);

        Call<List<Recipe>> slike = client.dohvatiglavna();

        slike.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if (response.isSuccessful()) {
                    recipe = response.body();
                    ArrayAdapter adapter = new RecipeAdapter(getApplicationContext(), R.layout.lista_recepata, (ArrayList<Recipe>) recipe);
                    list.setAdapter(adapter);
                    list.setTextFilterEnabled(true);
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }

        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RecipeDetail.class);
                //Object listItem = list.getAdapter().getItem(position);
                //String value = listItem.toString();
                intent.putExtra("item", recipe.get(position));
                startActivity(intent);
            }
        });}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }



    }





