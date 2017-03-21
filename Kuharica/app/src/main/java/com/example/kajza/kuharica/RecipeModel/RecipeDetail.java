package com.example.kajza.kuharica.RecipeModel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.kajza.kuharica.BaseActivity.BaseActivity;
import com.example.kajza.kuharica.MainActivity;
import com.example.kajza.kuharica.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class RecipeDetail extends BaseActivity {

    public Uri uri;
    Recipe mod;
    TextView recipeName;
    TextView recipeDescription;
    TextView recipeIngredient;
    TextView recipeInstructions;
    TextView recipeAmount;
    TextView recipeMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_detail_recipe);
        mod= getIntent().getExtras().getParcelable("item");

        //odabrani image id

        uri = Uri.parse(mod.getImage());
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image);
        draweeView.setImageURI(uri);

        recipeName = (TextView) findViewById(R.id.recipeName);
        recipeName.setText(mod.getName());

        recipeDescription = (TextView) findViewById(R.id.recipeDescription);
        recipeDescription.setText(mod.getDescription());

        recipeAmount = (TextView) findViewById(R.id.recipeAmount);
        recipeMeasure = (TextView) findViewById(R.id.recipeMeasure);
        recipeIngredient = (TextView) findViewById(R.id.recipeIngredient);
        recipeAmount.setText("\n" + mod.getIngredient());

        recipeInstructions = (TextView) findViewById(R.id.recipeInstructions);
        recipeInstructions.setText("\n" + mod.getInstructions());



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }



}
