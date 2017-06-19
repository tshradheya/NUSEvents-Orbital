package com.example.android.nusevents;

import static com.example.android.nusevents.model.EventInfo.isAdmin;

/**
 * Created by Admin on 16-Jun-17.
 */

public class User {

    public String name;
    public String email;
    public static boolean isAdministrator;
    public String uid;


    public User() {
        isAdministrator = false;
    }

    public User(String name, String email,String uid) {
        this.name = name;
        this.email = email;
        isAdministrator = false;
        this.uid = uid;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

