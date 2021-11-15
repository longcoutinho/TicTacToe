package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Home extends AppCompatActivity {
    public static int menu_type = 0;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button_play_online = (Button)findViewById(R.id.playonline);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable play_online_pressed_img = getResources().getDrawable(R.drawable.play_online_pressed_icon);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable play_online_img = getResources().getDrawable(R.drawable.play_online_icon);
        button_play_online.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                button_play_online.setCompoundDrawablesRelativeWithIntrinsicBounds(play_online_pressed_img, null, null, null);
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                button_play_online.setCompoundDrawablesRelativeWithIntrinsicBounds(play_online_img, null, null, null);
            }
            // TODO Auto-generated method stub
            return false;
        });
        Button button_play_with_bot = (Button)findViewById(R.id.playwithbot);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable play_with_bot_pressed_img = getResources().getDrawable(R.drawable.play_with_bot_pressed_icon);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable play_with_bot_img = getResources().getDrawable(R.drawable.play_with_bot_icon);
        button_play_with_bot.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                button_play_with_bot.setCompoundDrawablesRelativeWithIntrinsicBounds(play_with_bot_pressed_img, null, null, null);
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                button_play_with_bot.setCompoundDrawablesRelativeWithIntrinsicBounds(play_with_bot_img, null, null, null);
                playwithbot(v);
            }
            // TODO Auto-generated method stub
            return false;
        });

        Button button_play_with_friend = (Button)findViewById(R.id.twoplayers);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable play_with_friend_pressed_img = getResources().getDrawable(R.drawable.play_with_friend_pressed_icon);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable play_with_friend_img = getResources().getDrawable(R.drawable.play_with_friend_icon);
        button_play_with_friend.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                button_play_with_friend.setCompoundDrawablesRelativeWithIntrinsicBounds(play_with_friend_pressed_img, null, null, null);
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                button_play_with_friend.setCompoundDrawablesRelativeWithIntrinsicBounds(play_with_friend_img, null, null, null);
                twoplayers(v);
            }
            // TODO Auto-generated method stub
            return false;
        });

        Button button_setting= (Button)findViewById(R.id.setting);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable setting_pressed_img = getResources().getDrawable(R.drawable.setting_pressed_icon);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable setting_img = getResources().getDrawable(R.drawable.setting_icon);
        button_setting.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                button_setting.setCompoundDrawablesRelativeWithIntrinsicBounds(setting_pressed_img, null, null, null);
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                button_setting.setCompoundDrawablesRelativeWithIntrinsicBounds(setting_img, null, null, null);
            }
            // TODO Auto-generated method stub
            return false;
        });
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