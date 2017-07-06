package com.example.android.nusevents.model;

/**
 * Created by ronaklakhotia on 15/06/17.
 */

public class EventInfo {
    private String name;
    private String location;
    private long time;
    private String info;
    public String userCreated;
    private String owner;
    private String id;
    private String contact;
    //private String

public EventInfo()
{
    name="";
    time=0;
    location="random";
    info="...";
    userCreated="";
        owner="";
    id="";
    contact="";
}
public EventInfo(String a,long b,String c,String d,String e,String f,String id,String contact)
{
    name=a;
    time=b;
    location=c;
    info=d;
    owner=e;
    userCreated=f;
    this.id = id;
    this.contact=contact;
}
public String getName()
{
    return name;
}
public long getTime()
{
    return time;
}
public String getLocation()
{
    return location;
}
public String getInfo()
{
    return info;
}

public String getOwner()
{
    return owner;
}
public String getUserCreated(){return userCreated;}
    public String getId()
    {
        return id;
    }
    public String getContact()
    {
        return contact;
    }

}