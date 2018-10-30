package com.example.hasib.letsshare;

import android.content.Intent;
import android.graphics.Paint;
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
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView regester;
    FirebaseUser user;
    public static FirebaseDatabase mDatabase;
    private EditText email;
    private EditText pass;
    private Button signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mDatabase==null){
            mDatabase=FirebaseDatabase.getInstance();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        }

        mAuth = FirebaseAuth.getInstance();
        regester = (TextView) findViewById(R.id.regestered);
        regester.setPaintFlags(regester.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        email = (EditText) findViewById(R.id.emailsu);
        pass = (EditText) findViewById(R.id.passu);
        signin = (Button) findViewById(R.id.signup);

        //check if the user is already logged in or not
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), Signedin.class));
        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getmail = email.getText().toString().trim();
                String getpassword = pass.getText().toString().trim();
                callSignin(getmail,getpassword);

            }
        });
    }


    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),Signup.class));
    }

    private void callSignin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Signedin","signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(MainActivity.this,Signedin.class);
                            finish();
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Wrong Email or Password",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }



}
