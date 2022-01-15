package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayOnline extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ArrayList<Coordinates> posts = null;
    private TextView tv;
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_online);
        tv = findViewById(R.id.textView2);
        bt = findViewById(R.id.button);
        posts = new ArrayList<>();
        posts.add(new Coordinates(1, 2));
        posts.add(new Coordinates(2, 3));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("data").setValue(posts);
        Query newq = mDatabase.child("data");
        newq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Coordinates>> t = new GenericTypeIndicator<ArrayList<Coordinates>>() {};
                ArrayList<Coordinates> yourStringArray = snapshot.getValue(t);
                //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();
                String abc = "";
                for(int i = 0; i < yourStringArray.size(); i++)
                {
                    abc += yourStringArray.get(i).row + " " + yourStringArray.get(i).col + "\n";
                }
                tv.setText(abc);
                Log.v("long", "take data");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.v("long", posts.get(0) + " ");

            }
        });
    }

}