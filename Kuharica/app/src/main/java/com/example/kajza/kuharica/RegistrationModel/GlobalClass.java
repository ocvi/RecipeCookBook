package com.example.kajza.kuharica.RegistrationModel;

import android.app.Application;


public class GlobalClass extends Application {

    public String name;
    private String email;


    public String getName() {

        return name;
    }

    public void setName(String aName) {

        name = aName;

    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String aEmail) {

        email = aEmail;
    }

}
