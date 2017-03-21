package com.example.kajza.kuharica.RegistrationModel;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.kajza.kuharica.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity1 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText et_name = (EditText) findViewById(R.id.et_name);
        final EditText et_email = (EditText) findViewById(R.id.et_email);
        final EditText et_password = (EditText) findViewById(R.id.et_password);
        final ImageButton btn_register = (ImageButton) findViewById(R.id.btn_register);
        final TextView tv_login = (TextView) findViewById(R.id.tv_login);






        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent glavna = new Intent(getApplicationContext(), LoginActivity1.class);
                startActivity(glavna);
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = et_name.getText().toString();
                final String email = et_email.getText().toString().trim();
                final String password = et_password.getText().toString();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (TextUtils.isEmpty(username)) {
                                et_name.setError("Molimo vas unesite vaše korisničko ime!");
                                return;
                            }


                            if (TextUtils.isEmpty(password)) {
                                et_password.setError("Molimo vas unesite vašu lozinku!");
                                return;
                            }


                            if (TextUtils.isEmpty(email)) {
                                et_email.setError("Molimo vas da unesete vaš email!");
                                return;
                            }

                            if (!email.matches(emailPattern))
                            {
                                et_email.setError("Molimo vas da unesete ispravan email!");
                                return;
                            }


                            if (success) {

                                Toast.makeText(getApplicationContext(),"Registracija je uspjela!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity1.this, LoginActivity1.class);
                                RegisterActivity1.this.startActivity(intent);


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity1.this);
                                builder.setMessage("Register Failed").setNegativeButton("Retry", null).create().show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                };

                RegisterRequest registerRequest = new RegisterRequest(username, password, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity1.this);
                queue.add(registerRequest);



            }


        });
    }




    @Override
    public void onBackPressed() {
        return;
    }




};
