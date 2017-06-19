package com.example.android.nusevents;

import static com.example.android.nusevents.model.EventInfo.isAdmin;

/**
 * Created by Admin on 16-Jun-17.
 */

public class User {

    public String name;
    public boolean admin;
    public String uid;


    public User() {
        admin = false;
    }

    public User(String name,String uid) {
        this.name = name;
        admin = false;
        this.uid = uid;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin=admin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}


