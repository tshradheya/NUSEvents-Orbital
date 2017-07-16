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
    private long finishTime;
    private String date;
    private String photoUri;
    private String count;
    private boolean free;
    private String link;
    private boolean goodie;
    private boolean snacks;


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
    finishTime=0;
    date="";
    count="0";
    free=true;
    link="";
    goodie=false;
    snacks=false;


}
public EventInfo(String a,long b,String c,String d,String e,String f,String id,String contact,long z,String dd,String u,String count,boolean free,String link,boolean goodie,boolean snacks)
{
    name=a;
    time=b;
    location=c;
    info=d;
    owner=e;
    userCreated=f;
    this.id = id;
    this.contact=contact;
    finishTime=z;
    date=dd;
    photoUri=u;
    this.count=count;
    this.free=free;
    this.link=link;

    this.goodie=goodie;
    this.snacks=snacks;

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

    public long getFinishTime(){
        return finishTime;
    }
    public String getDate()
    {
        return date;
    }

    public String getPhotoUri(){
        return photoUri;
    }
    public String getCount(){return count;}

    public String getLink() {
        return link;
    }

    public boolean getFree() {
        return free;
    }


    public boolean getGoodie(){
        return goodie;

    }
    public boolean getSnacks(){
        return snacks;
    }

}