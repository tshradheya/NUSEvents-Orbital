package com.example.android.nusevents;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import static com.example.android.nusevents.EmailPasswordActivity.mAuth;

/**
 * Created by Admin on 22-Jun-17.
 */

public class EmailVerification extends AppCompatActivity  {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_email);

        final ProgressDialog pDialog = new ProgressDialog(this);



        pDialog.setMessage("Please Wait...");
        pDialog.show();

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        //findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            pDialog.hide();
                            AlertDialog.Builder myAlert = new AlertDialog.Builder(EmailVerification.this);

                            myAlert.setMessage("Email Verification sent!")
                                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();

                                            dialog.dismiss();


                                        }
                                    })
                                    .create();
                            myAlert.show();


                        } else {

                        }
                        // [END_EXCLUDE]
                    }
                });



    }
    }

