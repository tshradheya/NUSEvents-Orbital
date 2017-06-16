package com.example.android.nusevents.model;

/**
 * Created by ronaklakhotia on 15/06/17.
 */

public class EventInfo {
    private String name;
    private String time,location;
    private String info;
    public static boolean isAdmin;
    private String owner;

public EventInfo()
{
    name="";
    time="00:00";
    location="random";
    info="...";
    isAdmin=false;
    owner="";
}
public EventInfo(String a,String b,String c,String d,String e,boolean f)
{
    name=a;
    time=b;
    location=c;
    info=d;
    owner=e;
    isAdmin=f;
}
public String getName()
{
    return name;
}
public String getTime()
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
public boolean getisAdmin(){return isAdmin;}

}