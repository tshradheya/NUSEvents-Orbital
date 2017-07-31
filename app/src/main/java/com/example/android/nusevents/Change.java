package com.example.android.nusevents;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Change extends AppCompatActivity {

    EditText oldpass,newpass,emailid;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
    }
    public void UpdatePassword(View view) {
        oldpass = (EditText) findViewById(R.id.oldpass);
        newpass = (EditText) findViewById(R.id.newpass);
        emailid = (EditText) findViewById(R.id.emailid);

        if (newpass.getText().toString().length() < 9) {
            Toast.makeText(Change.this, "Password must be atleast 9 characters long.",
                    Toast.LENGTH_LONG).show();
            newpass.setText("");

        } else {


            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            AuthCredential credential = EmailAuthProvider
                    .getCredential(emailid.getText().toString(), oldpass.getText().toString());

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Password updated");
                                        } else {
                                            Toast.makeText(Change.this, "Password change unsuccessful",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(Change.this, "Password change unsuccessful",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            finish();
            Toast.makeText(Change.this, "Password has been changed",
                    Toast.LENGTH_LONG).show();


        }
    }
}
