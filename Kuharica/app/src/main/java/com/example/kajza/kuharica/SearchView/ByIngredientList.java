package com.example.kajza.kuharica.SearchView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kajza.kuharica.BaseActivity.BaseActivity;
import com.example.kajza.kuharica.R;
import com.example.kajza.kuharica.RecipeModel.Recipe;
import com.example.kajza.kuharica.RecipeModel.RecipeAdapter;
import com.example.kajza.kuharica.RecipeModel.RecipeDetail;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import static com.example.kajza.kuharica.R.drawable.recipe;

public class ByIngredientList extends BaseActivity {

    private ListView list;
    private List<Recipe> data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_list);
        data = getIntent().getParcelableArrayListExtra("result");
        list = (ListView) findViewById(R.id.list);


        ArrayAdapter adapter = new RecipeAdapter(getApplicationContext(), R.layout.lista_recepata, (ArrayList<Recipe>) data);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(), RecipeDetail.class);
                intent.putExtra("item", data.get(position));
                startActivity(intent);
            }


        });
    }
}
