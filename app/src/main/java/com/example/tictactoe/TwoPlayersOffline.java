package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class TwoPlayersOffline extends AppCompatActivity {
    public static String player1_name;
    public static String player2_name;
    public static int player1_avatar_id;
    public static int player2_avatar_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players_offline);
    }
    public void gototwoplayers(View v) {
        EditText editText1 = (EditText)findViewById(R.id.player1_name);
        player1_name = editText1.getText().toString();
        EditText editText2 = (EditText)findViewById(R.id.player2_name);
        player2_name = editText2.getText().toString();
        player1_avatar_id = getResources().getIdentifier("avatar_" + player1_name.toLowerCase(), "drawable", getPackageName());
        if (player1_avatar_id == 0) player1_avatar_id = getResources().getIdentifier("avatar_default", "drawable", getPackageName());
        player2_avatar_id = getResources().getIdentifier("avatar_" + player2_name.toLowerCase(), "drawable", getPackageName());
        if (player2_avatar_id == 0) player2_avatar_id = getResources().getIdentifier("avatar_default", "drawable", getPackageName());
        Intent newintent = new Intent(this, MainActivity.class);
        startActivity(newintent);
    }
}