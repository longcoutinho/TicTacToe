package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class InfoPage extends AppCompatActivity {
    public TextView infoname, infonotify, infomatchwon;
    public static String roomID;
    private DatabaseReference mDatabase;
    ArrayList<String> listofRooms;
    EditText et_roomID;
    Button joinroom;
    public ArrayList<Account> listofAccounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        infoname = (TextView) findViewById(R.id.tv_infoname);
        infoname.setText("Hello, " + LoginPage.playername);
        infomatchwon = (TextView) findViewById(R.id.tv_matchwin);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        et_roomID = (EditText) findViewById(R.id.et_roomid);
        infonotify = (TextView) findViewById(R.id.tv_notifyinfo);
        joinroom = (Button) findViewById(R.id.bt_joinroom);
        et_roomID.setVisibility(View.GONE);
        joinroom.setVisibility(View.GONE);
        infonotify.setVisibility(View.GONE);
        listofRooms = new ArrayList<>();
        Query newq = mDatabase.child("listroom");

        newq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                ArrayList<String> yourStringArray = snapshot.getValue(t);
                //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();

                try {
                    listofRooms.clear();
                    for(int i = 0; i < yourStringArray.size(); i++)
                    {
                        listofRooms.add(yourStringArray.get(i));
                    }
                }
                catch (NullPointerException e) {

                }

                //Log.v("long", "take data");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listofAccounts = new ArrayList<>();
        Query newq2 = mDatabase.child("listaccount");
        newq2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Account>> t = new GenericTypeIndicator<ArrayList<Account>>() {};
                ArrayList<Account> yourStringArray = snapshot.getValue(t);
                //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();

                try {
                    listofAccounts.clear();
                    for(int i = 0; i < yourStringArray.size(); i++)
                    {
                        listofAccounts.add(yourStringArray.get(i));
                    }
                    for(int i = 0; i < listofAccounts.size() ; i++)
                        if (listofAccounts.get(i).getUsername().equals(LoginPage.playerusername)) {
                            infomatchwon.setText("Matchs won: " + listofAccounts.get(i).getMatch_win());
                            break;
                        }
                }
                catch (NullPointerException e) {

                }

                //Log.v("long", "take data");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void newsignout(View v) {
        Intent newintent = new Intent(this, LoginPage.class);
        startActivity(newintent);
    }

    public void newcreateroom(View v) {
        Random r = new Random();
        int low = 1;
        int high = 10000;
        int result = r.nextInt(high-low) + low;
        InfoPage.roomID = Integer.toString(result);
        listofRooms.add(roomID);
        mDatabase.child("listroom").setValue(listofRooms);
        mDatabase.child(roomID).child("player_count").setValue(1);
        mDatabase.child(roomID).child("turn").setValue(1);
        mDatabase.child(roomID).child("player1_name").setValue(LoginPage.playername);
        mDatabase.child(roomID).child("pause").setValue(false);
        mDatabase.child(roomID).child("gameover").setValue(false);
        Intent newintent = new Intent(this, MainActivity.class);
        startActivity(newintent);
    }

    public void newjoinroom(View v) {
        int used = 0;
        for(int i = 0; i < listofRooms.size(); i++)
            if (listofRooms.get(i).equals(et_roomID.getText().toString())) {
                used = 1;
                break;
            }
        if (used == 0) {
            infonotify.setText("Room does not exist");
        }
        else {
            InfoPage.roomID = et_roomID.getText().toString();
            mDatabase.child(roomID).child("player2_name").setValue(LoginPage.playername);
            Intent newintent = new Intent(this, MainActivity.class);
            startActivity(newintent);
        }
    }

    public void newfindroom(View v) {
        et_roomID.setVisibility(View.VISIBLE);
        joinroom.setVisibility(View.VISIBLE);
        infonotify.setVisibility(View.VISIBLE);
    }
}