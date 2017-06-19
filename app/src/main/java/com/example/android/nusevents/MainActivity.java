package com.example.android.nusevents;

import android.content.Intent;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;

import com.example.android.nusevents.model.EventInfo;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import static android.view.View.Z;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static bolts.AppLinkNavigation.NavigationResult.APP;
import static com.example.android.nusevents.User.isAdministrator;
import static com.example.android.nusevents.model.EventInfo.isAdmin;
import static junit.runner.Version.id;


public class MainActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 1;

    private static int check = 1;

    public EventInfo object;


    private ChildEventListener mChildEventListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




       // userDatabase = FirebaseDatabase.getInstance();
       // userDatabaseReference = userDatabase.getReference().child("User");





        /*mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {

                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){
                    //signed in
                    check++;
                    String username=user.getDisplayName();
                    if(check==2)
                    Toast.makeText(MainActivity.this,"Hello "+username+"! You are now Signed In. Welcome to NUS Events APP!",Toast.LENGTH_SHORT).show();




                }
                else{
                    //signed out

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                   // object = new EventInfo();



                }


            }
        };



    }

    @Override
    public void onActivityResult(int requestCode, int responseCode,Intent data)
    {
        super.onActivityResult(requestCode,responseCode,data);

        if(requestCode==RC_SIGN_IN) {
            if (responseCode == RESULT_OK) {
                //Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT).show();
            } else if (responseCode == RESULT_CANCELED) {
                Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }
*/
    }
        @Override
        protected void onResume ()
        {
            super.onResume();

        }


        @Override
        protected void onPause ()
        {
            super.onPause();

            // mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.my_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){

            if (item.getItemId() == R.id.aboutUs) {
                Intent i = new Intent(this, About_Us.class);
                startActivity(i);
            }

            if (item.getItemId() == R.id.signOut) {
                check = 1;

                EmailPasswordActivity.signOut();
                finish();

                Intent intent = new Intent(this, EmailPasswordActivity.class);
                startActivity(intent);

                return true;
            }
            return true;
        }

    public void viewList(View view) {

        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        if (isAdministrator == false) {

            myAlert.setMessage("Contact the admins to get authenticated!")
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else {
            Intent i = new Intent(this, Details.class);
            startActivity(i);

        }
    }
    public void DisplayList(View view)
    {
        Intent i = new Intent(this,DisplayEventList.class);
        startActivity(i);
    }


}

