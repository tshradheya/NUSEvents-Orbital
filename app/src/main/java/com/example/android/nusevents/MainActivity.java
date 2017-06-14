package com.example.android.nusevents;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import static bolts.AppLinkNavigation.NavigationResult.APP;


public class MainActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN=1;

    private  static int check = 1;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth= FirebaseAuth.getInstance();




        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

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
                Toast.makeText(this, "Sign In Failed!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    protected void onPause()
    {
        super.onPause();

        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.aboutUs )
        {
            Intent i = new Intent(this,About_Us.class);
            startActivity(i);
        }

        if(item.getItemId()==R.id.signOut)
        {
            check = 1;
        FirebaseAuth.getInstance().signOut();
            return true;
        }
        return true;
    }

}
