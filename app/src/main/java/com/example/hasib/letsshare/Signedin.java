package com.example.hasib.letsshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.hasib.letsshare.MainActivity.mDatabase;

public class Signedin extends AppCompatActivity {
    Button Signout;
    Button chat;
    private FirebaseAuth mAuth;
   static String  Logged_in_user_email;
    TextView Username;
    public static int Device_Width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signedin);
        mAuth = FirebaseAuth.getInstance();
        Signout=(Button) findViewById(R.id.sout);
        Username= (TextView) findViewById(R.id.username);
        chat = (Button) findViewById(R.id.button2);
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        Device_Width = metrics.widthPixels;
        if (MainActivity.mDatabase==null){
            MainActivity.mDatabase= FirebaseDatabase.getInstance();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        }



        FirebaseUser user = mAuth.getCurrentUser();
        if (user!= null){
            Logged_in_user_email=user.getEmail();
            Username.setText("Hello!!," + user.getDisplayName());

        }
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Chatscreen.class));
            }
        });
        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
