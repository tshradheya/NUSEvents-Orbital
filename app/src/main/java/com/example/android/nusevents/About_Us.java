package com.example.android.nusevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About_Us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);
        String message;

        message = "NUSEvents is a collaborative effort on our part to provide the members of the NUS family with " +
                "an enriching campus life.We firmly believe that NUSEvents will have a positive impact on the " +
                "students and the members of the various faculties as it will provide an easy means for accessing the events " +
                "going around campus.This will ensure that everyone is aware of the happenings around the university " +
                "even if he/she lives off campus.We thus hope to make a difference to the NUS community." ;

        display(message);
    }

    public void display(String message)
    {
        TextView view = (TextView)findViewById(R.id.about);
        view.setText(message);
    }
    public void aboutAdmins(View view) {

        Intent i = new Intent(this,admin.class);
        startActivity(i);
    }

}
