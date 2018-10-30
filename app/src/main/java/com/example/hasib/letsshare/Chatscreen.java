package com.example.hasib.letsshare;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Chatscreen extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myref;
    public FirebaseRecyclerAdapter<Show_Chat_Activity_Data_items,Show_Chat_ViewHolder> mFirebaseAdapter;
    ProgressBar progressBar;
    LinearLayoutManager mlinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatscreen);

        firebaseDatabase=FirebaseDatabase.getInstance();
        myref= firebaseDatabase.getReference("Users");
        //myref.keepSynced(true);

        progressBar=(ProgressBar)findViewById(R.id.showchatprogress);
        recyclerView = (RecyclerView) findViewById(R.id.showchat);
        mlinearLayoutManager  = new LinearLayoutManager(Chatscreen.this);
        recyclerView.setLayoutManager(mlinearLayoutManager);
    }
    //recycler view get data from database
    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(ProgressBar.VISIBLE);        //on start progressbar will show while loading data from firebase
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Show_Chat_Activity_Data_items, Show_Chat_ViewHolder>(Show_Chat_Activity_Data_items.class,R.layout.show_chat_single_item,Show_Chat_ViewHolder.class,myref) {

            public void populateViewHolder(Show_Chat_ViewHolder viewHolder, Show_Chat_Activity_Data_items model, final int position) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);   //on populate data we will hide the progressbar

                    //viewHolder.Person_Name(model.getName());

                if (!model.getName().equals("Null")) {
                    viewHolder.Person_Name(model.getName());            //this methods will get the data from firebase with the help of data class
                    Log.v("getnam",model.getName());


                    //viewHolder.Person_Email(model.getEmail());
                    if(model.getEmail().equals(Signedin.Logged_in_user_email))  //me or notm k
                    {
                        //viewHolder.itemView.setVisibility(View.GONE);
                        viewHolder.Layout_hide();

                        //recyclerView.getChildAdapterPosition(viewHolder.itemView.getRootView());
                         //viewHolder.itemView.setEnabled();


                    }
                    else
                        viewHolder.Person_Email(model.getEmail());
                }
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String retrieve_name = dataSnapshot.child("Name").getValue(String.class);
                                String retrieve_Email = dataSnapshot.child("Email").getValue(String.class);




                                Intent intent = new Intent(getApplicationContext(), ChatConversation.class);

                                intent.putExtra("email", retrieve_Email);
                                intent.putExtra("name", retrieve_name);

                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });

                }


           
        };
        recyclerView.setAdapter(mFirebaseAdapter);



    }
    //view holder class for getting data from database
    public static class Show_Chat_ViewHolder extends RecyclerView.ViewHolder {
        private final TextView person_name, person_email;
        private final ImageView person_image;
        private final LinearLayout layout;
        final LinearLayout.LayoutParams params;

        public Show_Chat_ViewHolder(final View itemView) {
            super(itemView);
            person_name = (TextView) itemView.findViewById(R.id.Chat_person_name);
            person_email = (TextView) itemView.findViewById(R.id.Chat_person_email);
            person_image = (ImageView) itemView.findViewById(R.id.Chat_person);
            layout = (LinearLayout)itemView.findViewById(R.id.Show_chat_single_item);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        private void Person_Name(String title) {
            // Log.d("LOGGED", "Setting Name: ");
            person_name.setText(title);
        }
        private void Layout_hide() {
            params.height = 0;
            //itemView.setLayoutParams(params);
            layout.setLayoutParams(params);

        }


        private void Person_Email(String title) {
            person_email.setText(title);
        }


        private void Person_Image(String url) {

            if (!url.equals("Null")) {
                Glide.with(itemView.getContext())
                        .load(url)
                        .crossFade()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(person_image);
            }

        }


    }


}
