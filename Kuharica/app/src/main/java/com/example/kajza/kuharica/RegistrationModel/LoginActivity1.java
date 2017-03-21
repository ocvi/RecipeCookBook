package com.example.kajza.kuharica.RegistrationModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.kajza.kuharica.MainActivity;
import com.example.kajza.kuharica.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity1 extends AppCompatActivity {

    private CheckBox saveLoginCheckBox;
    public SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private String username,password;
    private EditText et_name, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText et_password = (EditText) findViewById(R.id.et_password);
        final EditText et_name = (EditText) findViewById(R.id.et_name);
        final ImageButton btn_login = (ImageButton) findViewById(R.id.btn_login);
        final TextView tv_register = (TextView) findViewById(R.id.tv_register);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);


        if (saveLogin == true) {
            et_name.setText(loginPreferences.getString("username", ""));
            et_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }


        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent glavna = new Intent(getApplicationContext(), RegisterActivity1.class);
                startActivity(glavna);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_name.getText().toString();
                password = et_password.getText().toString();

                if(TextUtils.isEmpty(username)) {
                    et_name.setError("Molimo vas unesite vaše korisničko ime!");
                    return;
                }



                if(TextUtils.isEmpty(password)) {
                    et_password.setError("Molimo vas unesite vašu lozinku!");
                    return;
                }



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(LoginActivity1.this, MainActivity.class);
                                LoginActivity1.this.startActivity(intent);
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(et_name.getWindowToken(), 0);

                                username = et_name.getText().toString();
                                password = et_password.getText().toString();

                                if (saveLoginCheckBox.isChecked()) {
                                    loginPrefsEditor.putBoolean("saveLogin", true);
                                    loginPrefsEditor.putString("username", username);
                                    loginPrefsEditor.putString("password", password);
                                    loginPrefsEditor.commit();
                                } else {
                                    loginPrefsEditor.clear();
                                    loginPrefsEditor.commit();
                                }

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity1.this);
                                builder.setMessage("Login Failed").setNegativeButton("Retry", null).create().show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity1.this);
                queue.add(loginRequest);


            }


        });





    }



    @Override
    public void onBackPressed() {
        return;
    }
}
