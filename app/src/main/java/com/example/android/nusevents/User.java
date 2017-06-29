package com.example.android.nusevents;


/**
 * Created by Admin on 16-Jun-17.
 */

public class User {

    public String displayName;
    public String username;
    public String name;
    public boolean admin;
    public String uid;


    public User() {
        admin = false;
    }

    public User(String name,String uid,String displayName,String username) {
        this.displayName=displayName;
        this.username=username;
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

    public String getDisplayName(){return displayName; }

    public void setDisplayName(String displayName){
        this.displayName=displayName;

    }

    public String getUsername(){return username; }

    public void setUsername(String username){
        this.username=username;

    }



}


