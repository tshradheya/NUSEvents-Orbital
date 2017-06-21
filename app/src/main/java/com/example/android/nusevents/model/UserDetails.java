package com.example.android.nusevents.model;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.nusevents.EmailPasswordActivity;
import com.example.android.nusevents.R;
import com.example.android.nusevents.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.android.nusevents.R.id.display_username;

/**
 * Created by Admin on 21-Jun-17.
 */

public class UserDetails extends AppCompatActivity {

    public static String name;
    public static String username;

    public EditText display_name, display_username;

    public Button saveButton;

    private static DatabaseReference userReference;

    public static String email,uri;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_layout);


        display_name = (EditText) findViewById(R.id.display_name);
        display_username = (EditText) findViewById(R.id.display_username);

        saveButton = (Button) findViewById(R.id.save);

    }


    public static void addIntoDatabase(DatabaseReference userDatabaseReference,String a, String b)
    {

        userReference=userDatabaseReference;


        email=a;
        uri=b;


    }


    public void save(View view) {

        name = display_name.getText().toString();
        username = display_username.getText().toString();

        //EmailPasswordActivity.passDetails(name, username);

        User currUser= new User(email,uri,name,username);

        userReference.push().setValue(currUser);

        finish();
    }


}


