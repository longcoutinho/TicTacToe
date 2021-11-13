package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Home extends AppCompatActivity {
    public static int menu_type = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void twoplayers(View v) {
        Intent newintent = new Intent(this, TwoPlayersOffline.class);
        startActivity(newintent);
        menu_type = 2;
    }
    public void playwithbot(View v) {
        Intent newintent = new Intent(this, PlayWithBot.class);
        startActivity(newintent);
        menu_type = 1;
    }
}