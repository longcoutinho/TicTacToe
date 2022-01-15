package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

public class SignUpPage extends AppCompatActivity {
    public Button signupbt;
    public Button loginbt;
    public TextView notify;
    public EditText name, username, password, confirmpw;
    private DatabaseReference mDatabase;
    public ArrayList<Account> listofAccounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        signupbt = (Button) findViewById(R.id.btsu_signup);
        loginbt = (Button) findViewById(R.id.btsu_login);
        notify = (TextView) findViewById(R.id.notifytext);
        notify.setText("");
        name = (EditText) findViewById(R.id.et_playername);
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        confirmpw = (EditText) findViewById(R.id.et_confirmpassword);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listofAccounts = new ArrayList<>();
        Query newq = mDatabase.child("listaccount");
        newq.addValueEventListener(new ValueEventListener() {
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
    public void newlogin(View v) {
        Intent newintent = new Intent(this, LoginPage.class);
        startActivity(newintent);
    }
    public void newsignup(View v) {
        if (name.getText().toString().length() < 1 || name.getText().toString().length() > 5) {
            notify.setText("Player name 's length between 1 and 5 words");
        }
        else if (username.getText().toString().length() < 6 || username.getText().toString().length() > 12) {
            notify.setText("Username 's length between 6 and 12 words");
        }
        else if (password.getText().toString().length() < 6 || username.getText().toString().length() > 12) {
            notify.setText("Password 's length between 6 and 12 words");
        }
        else if (!password.getText().toString().equals(confirmpw.getText().toString())) {
            notify.setText("Confirm password doesn't match");
        }
        else {
            int kt = 0;
            for(int i = 0; i < listofAccounts.size() ; i++)
                if (listofAccounts.get(i).getUsername().equals(username.getText().toString())) {
                    kt = 1;
                    break;
                }
            if (kt == 1) notify.setText("Username already exists");
            else {
                notify.setText("Sign up successfully. Click login button to login");
                Account newaccount = new Account(name.getText().toString(), username.getText().toString(), password.getText().toString(), 0);
                listofAccounts.add(newaccount);
                mDatabase.child("listaccount").setValue(listofAccounts);
            }
        }

    }
}