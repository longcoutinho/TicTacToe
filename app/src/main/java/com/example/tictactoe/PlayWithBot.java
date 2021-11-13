package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlayWithBot extends AppCompatActivity {
    public static boolean player1isBot;
    public static boolean player2isBot;
    public static String player1_name;
    public static String player2_name;
    public static int player1_avatar_id;
    public static int player2_avatar_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_bot);
    }
    public void clickyes(View v) {
        player2isBot = true;
        player1isBot = false;
        player1_name = "LONG";
        player2_name = "BOT";
        player1_avatar_id = getResources().getIdentifier("avatar_default", "drawable", getPackageName());
        player2_avatar_id = getResources().getIdentifier("avatar_bot", "drawable", getPackageName());
        Intent newintent = new Intent(this, MainActivity.class);
        startActivity(newintent);
    }
    public void clickno(View v) {
        player1isBot = true;
        player2isBot = false;
        player1_name = "BOT";
        player2_name = "LONG";
        player1_avatar_id = getResources().getIdentifier("avatar_bot", "drawable", getPackageName());
        player2_avatar_id = getResources().getIdentifier("avatar_default", "drawable", getPackageName());
        Intent newintent = new Intent(this, MainActivity.class);
        startActivity(newintent);
    }
}