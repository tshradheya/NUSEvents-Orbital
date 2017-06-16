package com.example.android.nusevents;

import static com.example.android.nusevents.model.EventInfo.isAdmin;

/**
 * Created by Admin on 16-Jun-17.
 */

public class User {

    public String name;
    public String email;
    public boolean isAdministrator;
    public String uid;



    public User()
    {
        isAdministrator=false;
    }

    public User(String name,String email)
    {
        this.name=name;
        this.email=email;
        isAdministrator=false;

    }


    public String getName()
    {
        return name;

    }

    public String getEmail()
    {
        return  email;

    }

    public boolean isAdmin()
    {
        return isAdministrator;

    }

    public String getUid()
    {
        return uid;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public void setEmail(String email)
    {
        this.email=email;

    }

    public void setUid(String uid)
    {
        this.uid=uid;

    }

    public void setAdministrator(boolean val)
    {
        isAdministrator=val;
    }

}
