package com.example.kajza.kuharica.RecipeModel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kajza.kuharica.BaseActivity.BaseActivity;
import com.example.kajza.kuharica.MainActivity;
import com.example.kajza.kuharica.R;
import com.example.kajza.kuharica.api.InterfacePremaServisu;
import com.example.kajza.kuharica.api.ServiceGenerator;
import com.example.kajza.kuharica.api.response.RecipeUploadResponse;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class UnosRecepta extends BaseActivity {

    private static final String API_URL = "http://kajza.comxa.com/";
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;

    final int CAMERA_REQUEST = 13323;
    final int GALLERY_REQUEST = 22131;
    String selectedPhoto;

    private EditText name_txt;
    private EditText cat_txt;
    private EditText ing_txt;
    private EditText desc_txt;
    private EditText inst_txt;
    private ImageButton iv_camera;
    private ImageButton iv_gallery;
    private ImageButton iv_upload;
    private ImageView iv_image;

    //View parentView;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unos_recepta);


        name_txt = (EditText) findViewById(R.id.name_txt);
        ing_txt = (EditText) findViewById(R.id.ing_txt);
        desc_txt = (EditText) findViewById(R.id.desc_txt);
        inst_txt = (EditText) findViewById(R.id.inst_txt);
        spinner = (Spinner) findViewById(R.id.cat_spinner);


        adapter = ArrayAdapter.createFromResource(this,R.array.category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());



        iv_image = (ImageView)findViewById(R.id.iv_image);
        iv_camera = (ImageButton)findViewById(R.id.iv_camera);
        iv_gallery = (ImageButton)findViewById(R.id.iv_gallery);
        iv_upload = (ImageButton)findViewById(R.id.iv_upload);





        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                    cameraPhoto.getPhotoPath();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while taking photos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });



        iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(selectedPhoto)) {
                    unosReceptainfo();
                }
            }
        });}




    private void unosReceptainfo() {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(UnosRecepta.this);
        progressDialog.setMessage("Pričekajte!");
        progressDialog.show();


        // Upload Client
        InterfacePremaServisu client = ServiceGenerator.createService(InterfacePremaServisu.class, API_URL);

        //File creating from selected URL

       /* Recipe recipe = new Recipe();
        recipe.setName(name_txt.getText().toString());
        recipe.setCategory(cat_txt.getText().toString());
        recipe.setIngredient(ing_txt.getText().toString());
        recipe.setDescription(desc_txt.getText().toString());
        recipe.setInstructions(inst_txt.getText().toString());
        */


        String name = name_txt.getText().toString();
        String category = spinner.getSelectedItem().toString();
        String ingredient = ing_txt.getText().toString();
        String description = desc_txt.getText().toString();
        String instructions = inst_txt.getText().toString();

        File file = new File(selectedPhoto);


        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        String nameString = name;
        RequestBody ime =
                RequestBody.create(
                        MediaType.parse("name"), nameString);

        String descriptionString = description;
        RequestBody opis =
                RequestBody.create(
                        MediaType.parse("description"), descriptionString);

        String categoryString = category;
        RequestBody kategorija =
                RequestBody.create(
                        MediaType.parse("category"), categoryString);

        String ingredientString = ingredient;
        RequestBody sastojak =
                RequestBody.create(
                        MediaType.parse("ingredient"), ingredientString);

        String instructionsString = instructions;
        RequestBody instrukcije =
                RequestBody.create(
                        MediaType.parse("instructions"), instructionsString);

        Call<RecipeUploadResponse> resultCall = client.unosReceptainfo(ime, kategorija, opis, sastojak, instrukcije, body);



        // finally, execute the request
        resultCall.enqueue(new Callback<RecipeUploadResponse>() {

            @Override
            public void onResponse(Call<RecipeUploadResponse> call, retrofit2.Response<RecipeUploadResponse> response) {

                progressDialog.dismiss();



                if (response.body().isSuccess()) {
                    Toast.makeText(UnosRecepta.this,"Hvala vam! Vaš recept je zaprimljen i poslan na razmatranje!", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(UnosRecepta.this, MainActivity.class);
                    startActivity(intent2);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Neuspjelo slanje recepta, pokušajte ponovno!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RecipeUploadResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UnosRecepta.this,"onFailure", Toast.LENGTH_SHORT).show();

                t.printStackTrace();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){

                String photoPath = cameraPhoto.getPhotoPath();
                selectedPhoto = photoPath;
                try {

                    Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512, 512).getBitmap();
                    iv_image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Došlo je do pogreške prilikom odabira slike, pokušajte ponovno!", Toast.LENGTH_SHORT).show();
                }

            }
            else if(requestCode == GALLERY_REQUEST){
                Uri uri = data.getData();

                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512, 512).getBitmap();
                    iv_image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Došlo je do pogreške prilikom odabira slike, pokušajte ponovno!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



}
