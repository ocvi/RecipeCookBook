package com.example.kajza.kuharica.SearchView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kajza.kuharica.BaseActivity.BaseActivity;
import com.example.kajza.kuharica.R;
import com.example.kajza.kuharica.RecipeModel.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SearchByIngredient extends BaseActivity {

    ImageButton trazi_btn;
    ImageButton btnAdd;
    List<String> responseList;
    ArrayAdapter myAdapter;
//    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.editRow);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_ingredient_search_test);
        btnAdd = (ImageButton) findViewById(R.id.gumbAdd);
        trazi_btn = (ImageButton) findViewById(R.id.trazi_btn);


        new DohvatImenaSastojaka().execute();






        //SearchByIngredientDynamicLayout.add(this, btnAdd);

        // SearchByIngredientDynamicLayout.search(this, btnDisplay);

        trazi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fetch layout surrounding editTexts
                LinearLayout scrollViewlinerLayout = (LinearLayout)findViewById(R.id.linearLayoutForm);
                // prepare list to collect ingredients from existing editTexts
                List<String> searchQueries = new ArrayList<String>();
                // go through each scrollViewLinearLayout child to fetch existing EditTexts
                for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++) {
                    LinearLayout editTextContainer = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
                    AutoCompleteTextView edit = (AutoCompleteTextView) editTextContainer.findViewById(R.id.editRow);
                    // fetch entered value from EditText field and save it to the ingredients
                    // TODO add check to see if there is any input - add to list only if there is text in the EditText
                    searchQueries.add(edit.getText().toString());
                }

                if (searchQueries.isEmpty()){

                    Toast.makeText(SearchByIngredient.this,"Unesite sastojak te pokušajte opet!", Toast.LENGTH_LONG).show();
                }else{

                    // initiate call to server
                    new DohvatPoSastojku(searchQueries).execute();
                }



            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {

            final LinearLayout linearLayoutForm = (LinearLayout) findViewById(R.id.linearLayoutForm);
            @Override
            public void onClick(View v) {

                if(linearLayoutForm.getChildCount() == 2){
                    btnAdd.setEnabled(false);
                }



                for (int i = 0; i < linearLayoutForm.getChildCount(); i++) {
                    LinearLayout editTextContainer = (LinearLayout) linearLayoutForm.getChildAt(i);
                    final AutoCompleteTextView edit = (AutoCompleteTextView) editTextContainer.findViewById(R.id.editRow);
                    //edit.setAdapter(myAdapter);

                    edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {



                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            //new DohvatImenaSastojaka();

                            edit.setAdapter(myAdapter);
                            //AutoCompleteTextView edit = (AutoCompleteTextView) findViewById(R.id.editRow);



                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                }
                final LinearLayout newView = (LinearLayout)getLayoutInflater().inflate(R.layout.test_row, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        linearLayoutForm.removeView(newView);
                        btnAdd.setEnabled(true);
                    }
                });
                linearLayoutForm.addView(newView);

            }
        });



    }

    public  class DohvatPoSastojku extends AsyncTask<Void, String, String>{

        ProgressDialog pdLoading = new ProgressDialog(SearchByIngredient.this);

        HttpURLConnection conn;
        URL url = null;
        // String searchQuery1, searchQuery2, searchQuery3;
        List<String> searchQueryList;

        // get message list - list of searchQueries
        public DohvatPoSastojku(List<String> searchQueries) {

            this.searchQueryList = searchQueries;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }


        @Override
        protected String doInBackground(Void... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://kajza.comxa.com/search/SearchByIngredients2.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput to true as we send and recieve data
                conn.setDoInput(true);
                conn.setDoOutput(true);
                String query;
                // add parameter to our above url
                Uri.Builder builder = new Uri.Builder();

                // go through the list of messages and add appropriate number of query parameters
                for (int i = 0; i < searchQueryList.size(); i++) {
                    // add numbered searchQuery parameter for each message (as many as there are) - searchQuery1, searchQuery2, etc.
                    builder.appendQueryParameter("searchQuery" + (i+1), searchQueryList.get(i));
                }

                query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    return ("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            pdLoading.dismiss();
            List<Recipe> data = new ArrayList<>();

            pdLoading.dismiss();
            if (result.equals("no rows")) {
                Toast.makeText(getApplicationContext(), "No Results found for entered query", Toast.LENGTH_LONG).show();
            } else {

                try {

                    JSONArray jArray = new JSONArray(result);


                    // Extract data from json and store into ArrayList as class objects

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject json_data = jArray.getJSONObject(i);
                        Recipe recipe = new Recipe();
                        recipe.name = json_data.getString("name");
                        recipe.description = json_data.getString("description");
                        recipe.instructions = json_data.getString("instructions");
                        recipe.ingredient = json_data.getString("ingredient");
                        recipe.image = json_data.getString("image");

                        data.add(recipe);


                    }

                    Intent intent = new Intent(SearchByIngredient.this, ByIngredientList.class);
                    intent.putExtra("result", (ArrayList<Recipe>) data);
                    startActivity(intent);


                } catch (JSONException e) {
                    // You to understand what actually error is and handle it appropriately
                    Toast.makeText(SearchByIngredient.this, e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(SearchByIngredient.this, result.toString(), Toast.LENGTH_LONG).show();
                }

            }

        }
    }

    // Create class AsyncFetch
    public class DohvatImenaSastojaka extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(SearchByIngredient.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides or your JSON file address
                url = new URL("http://kajza.comxa.com/search/Ingredients.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we receive data
                conn.setDoOutput(true);
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    return("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
           // ArrayList<String> dataList = new ArrayList<String>();
            responseList = new ArrayList<String>();
            pdLoading.dismiss();


            if(result.equals("no rows")) {

                // Do some action if no data from database

            }else{

                try {

                    JSONArray jArray = new JSONArray(result);

                    // Extract data from json and store into ArrayList
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        responseList.add(json_data.getString("IgredientName"));
                    }

                   // strArrData = dataList.toArray(new String[dataList.size()]);







                } catch (JSONException e) {
                    // You to understand what actually error is and handle it appropriately
                    Toast.makeText(SearchByIngredient.this, e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(SearchByIngredient.this, result.toString(), Toast.LENGTH_LONG).show();
                }

            }

        }




    }

}



