package com.example.hasib.letsshare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText name;
    private EditText email;
    private EditText pass;
    private Button signup;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.namesup);
        email = (EditText) findViewById(R.id.emailsup);
        pass = (EditText) findViewById(R.id.paaaaas);
        signup = (Button) findViewById(R.id.signupp);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getmail = email.getText().toString().trim();
                String getpassword = pass.getText().toString().trim();
                callSignnup(getmail,getpassword);
            }
        });

    }
    private void callSignnup(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {





                            userProfile();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID = user.getEmail().replace("8","").replace(".","");
                            DatabaseReference mRootref = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference refl = mRootref.child("Users").child(userID);
                            refl.child("Name").setValue(name.getText().toString().trim());
                            refl.child("Email").setValue(user.getEmail());
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(),"Created Account",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Signedin.class));


                        } else {

                            // If sign in fails, display a message to the user.
                            Log.w( "createU:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    private void userProfile(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name.getText().toString().trim()).build();
         user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()){
                     Log.d("Testing","User Profile Updated");
                 }
             }
         });
        }


    }

}

