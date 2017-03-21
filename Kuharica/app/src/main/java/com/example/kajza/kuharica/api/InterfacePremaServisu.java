package com.example.kajza.kuharica.api;

import com.example.kajza.kuharica.RecipeModel.Recipe;
import com.example.kajza.kuharica.api.response.RecipeUploadResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface InterfacePremaServisu {
    @GET("predjela/predjelo.php")
    Call<List<Recipe>> dohvatipredjela();
    @GET("salate/salate.php")
    Call<List<Recipe>> dohvatisalate();
    @GET("glavna/glavnoJelo.php")
    Call<List<Recipe>> dohvatiglavna();
    @GET("deserti/desertiReloaded.php")
    Call<List<Recipe>> dohvatideserte();


    @Multipart
    @POST("images/upload.php")
    Call<RecipeUploadResponse> unosReceptainfo(
            @Part("name") RequestBody name,
            @Part("category") RequestBody category,
            @Part("ingredient") RequestBody ingredient,
            @Part("description") RequestBody description,
            @Part("instructions") RequestBody instructions,
            @Part MultipartBody.Part image);







}
